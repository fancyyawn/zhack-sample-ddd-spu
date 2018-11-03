package top.zhacker.retail.spu.domain.spu;

import com.google.common.collect.Sets;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import top.zhacker.boot.event.publish.DomainEventPublisher;
import top.zhacker.boot.registry.DomainRegistry;
import top.zhacker.boot.validate.ListDistinct;
import top.zhacker.core.model.IdentifiedEntity;
import top.zhacker.retail.spu.domain.category.model.Category;
import top.zhacker.retail.spu.domain.category.model.CategoryId;
import top.zhacker.retail.spu.domain.category.CategoryRepo;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.sku.model.Sku;
import top.zhacker.retail.spu.domain.sku.model.SkuOperation;
import top.zhacker.retail.spu.domain.sku.SkuRepo;
import top.zhacker.retail.spu.domain.spu.code.SpuBarCodeTuple;
import top.zhacker.retail.spu.domain.spu.code.SpuNo;
import top.zhacker.retail.spu.domain.spu.event.*;
import top.zhacker.retail.spu.domain.spu.photo.PhotoTuple;
import top.zhacker.retail.spu.domain.spu.spec.SpecDefineTuple;
import top.zhacker.retail.spu.domain.spu.spec.SpecTuple;
import top.zhacker.retail.spu.domain.unit.model.Unit;
import top.zhacker.retail.spu.domain.unit.model.UnitId;
import top.zhacker.retail.spu.domain.unit.UnitRepo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


/**
 * Created by zhacker.
 * Time 2018/8/17 上午10:32
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Builder
@Slf4j
public class Spu extends IdentifiedEntity {
  
  @NotNull(message = "商家不能为空")
  private ShopId shopId;
  
  @NotNull(message = "ID不能为空")
  private SpuId spuId;
  
  @NotNull(message = "名称不可为空")
  @Size(max = 100, min = 1, message = "名称字符数为1到100个字")
  private String name;
  
  @NotNull(message = "编码不能为空")
  private SpuNo no;
  
  @NotNull(message = "条码组不能为空")
  private SpuBarCodeTuple barCodes;
  
  @NotNull(message = "图片不能为空")
  private PhotoTuple photoTuple;
  
  @NotNull(message = "分类不能为空")
  private CategoryId categoryId;
  
  private Category category;

  @NotNull(message = "单位不能为空")
  private UnitId unitId;
  
  private Unit unit;
  
  @NotNull(message = "规格定义不能为空")
  private SpecDefineTuple specDefineTuple;

  @ListDistinct(message = "规格不能重复")
  @Size(max = 600, message = "规格数最大不能超过600")
  private List<Sku> skus = new ArrayList<>();
  
  
  /**
   * 是否为无规格
   *
   * @return
   */
  public Boolean isNoSpecs(){
    return specDefineTuple.getDefines().isEmpty();
  }
  
  
  protected Spu(){
  
  }
  
  public Spu(
      ShopId shopId,
      SpuId spuId,
      SpuNo no,
      SpuBarCodeTuple barCodes,
      String name,
      PhotoTuple photoTuple,
      Category category,
      Unit unit,
      SpecDefineTuple specDefineTuple,
      List<Sku> skuTuple) {
    this.shopId = shopId;
    this.spuId = spuId;
    this.name = name;
    this.no = no;
    this.barCodes= barCodes;
    this.photoTuple = photoTuple;
    this.category = category;
    this.categoryId = category.getCategoryId();
    this.unit = unit;
    this.unitId = unit.getUnitId();
    this.specDefineTuple = specDefineTuple;
    this.skus = skuTuple;
    
    super.validate();
    validateSkus(this);
    
    DomainEventPublisher.publish(new SpuCreatedEvent()
        .setShopId(shopId)
        .setSpuId(spuId)
    );
  }


  protected static void validateSkus(Spu spu){
    spu.validate("skus");
    val skuGroups = spu.groupSkuBySpecTuple();
    spu.assertArgumentTrue(skuGroups.values().stream().allMatch(list->list.size()<=1), "存在重复的规格组");
    spu.assertArgumentTrue(spu.specDefineTuple.cartesianProductEquals(skuGroups.keySet()), "与规格定义中的笛卡尔积不相符");
  }
  
  
  public static class SpuBuilder {
  
    private ShopId shopId;
    private SpuId spuId;
    private SpuNo no;
    private SpuBarCodeTuple barCodes;
    private String name;
    private PhotoTuple photoTuple;
    private Category category;
    private Unit unit;
    private SpecDefineTuple specDefineTuple;
    private List<Sku> skuTuple;
    
    
    public Spu build(){
      return new Spu(shopId,spuId,no,barCodes,name,photoTuple,category,unit,specDefineTuple,skuTuple);
    }
  }
  
  
  /**
   * 加载分类
   *
   * @return
   */
  public Spu loadCategory(){
    if(this.category!=null){
      return this;
    }
    if(categoryId!=null){
      this.category = DomainRegistry.repo(CategoryRepo.class).findByShopIdAndId(shopId, categoryId);
    }
    return this;
  }
  
  
  /**
   * 加载单位
   *
   * @return
   */
  public Spu loadUnit(){
    if(this.unit!=null){
      return this;
    }
    if(unitId!=null){
      this.unit = DomainRegistry.repo(UnitRepo.class).findById(shopId, unitId);
    }
    return this;
  }
  
  
  /**
   * 加载sku
   *
   * @return
   */
  public Spu loadSkuTuple(){
    if(skus!=null && ! skus.isEmpty()){
      return this;
    }
    val skus = DomainRegistry.repo(SkuRepo.class).findBySpuId(shopId, spuId);
    this.skus = skus;
    return this;
  }
  
  public Spu changeName(String name){
    if(Objects.equals(name,this.name)){
      return this;
    }
    new Spu().setName(name).validate("name");
  
    DomainEventPublisher.publish(new SpuNameChangedEvent()
        .setShopId(shopId)
        .setSpuId(spuId)
        .setOrigin(this.name)
        .setTarget(name)
    );
    
    this.name = name;
    
    return this;
  }
  
  public Spu changeCategory(Category category){
    
    assertArgumentNotNull(category, "分类参数不为空");
    assertArgumentEquals(this.shopId, category.getShopId(), "所属商户不同");
    
    if(Objects.equals(category.getCategoryId(), this.categoryId)){
      log.debug("skip change category because the same category={}", category);
      return this;
    }
  
    DomainEventPublisher.publish(new SpuCategoryChangedEvent()
        .setShopId(shopId)
        .setSpuId(spuId)
        .setOrigin(this.categoryId)
        .setTarget(category.getCategoryId())
    );
  
    
    this.category = category;
    this.categoryId = category.getCategoryId();
    
    
    return this;
  }
  
  
  public Spu changeUnit(Unit unit) {
    assertArgumentNotNull(unit, "单位不能为空");
    assertArgumentEquals(this.shopId, unit.getShopId(), "所属商户不同");
    
    if(Objects.equals(unit.getUnitId(), this.unitId)){
      log.debug("skip change unit because the same unit={}", unit);
      return this;
    }
  
    DomainEventPublisher.publish(new SpuUnitChangedEvent()
        .setShopId(shopId)
        .setSpuId(spuId)
        .setOrigin(this.unitId)
        .setTarget(unit.getUnitId())
    );
    
    this.unit = unit;
    this.unitId = unit.getUnitId();
    
    return this;
  }
  
  public Spu changeBarCodes(SpuBarCodeTuple tuple){
    assertArgumentNotNull(tuple, "条码组不能为空");
    
    if(Objects.equals(tuple, this.barCodes)){
      return this;
    }
  
    DomainEventPublisher.publish(new SpuBarCodesChangedEvent()
        .setShopId(shopId)
        .setSpuId(spuId)
        .setOrigin(this.barCodes)
        .setTarget(tuple));
    
    this.barCodes = tuple;
    
    return this;
  }
  
  public Spu changePhotoTuple(PhotoTuple tuple) {
    assertArgumentNotNull(tuple, "图组不能为空");
    
    if(Objects.equals(tuple, this.photoTuple)){
      return this;
    }
  
    DomainEventPublisher.publish(new SpuPhotoChangedEvent()
        .setShopId(shopId)
        .setSpuId(spuId)
        .setOrigin(this.photoTuple)
        .setTarget(tuple)
    );
    
    this.photoTuple = tuple;
    
    return this;
  }
  
  public Spu addSpecs(SpecDefineTuple specDefineTuple, List<Sku> skusToAdd) {

    this.loadSkuTuple();

    val skus = mergeList(this.getSkus(), skusToAdd);

    validateSkus(new Spu().setSpecDefineTuple(specDefineTuple).setSkus(skus));
    
    this.specDefineTuple = specDefineTuple;
    this.skus = skus;

    DomainEventPublisher.publish(new SpuAddSpecEvent()
            .setShopId(shopId)
            .setSpuId(spuId)
            .setOrigin(this.specDefineTuple)
            .setTarget(specDefineTuple)
            .setSkusAdded(skusToAdd.stream().map(Sku::getSkuId).collect(toList()))
    );
    
    return this;
  }
  
  @SafeVarargs
  private static <T>  List<T> mergeList(List<T> ... args){
    List<T> list = new ArrayList<>();
    for(List<T> t : args){
      list.addAll(t);
    }
    return list;
  }


  /**
   * 根据规格组排序
   * @return
   */
  public Spu sortSkuBySpecDefineTuple() {
    if (specDefineTuple == null) {
      return this;
    }

    if (skus == null) {
      return this;
    }

    if(skus.size()<=1){
      return this; //如果只有一个sku则不用排序
    }

    val specTuples = skus.stream().map(Sku::getSpecTuple).collect(Collectors.toList());
    specDefineTuple.fillSpecTupleOrder(specTuples);

    skus.sort(Comparator.comparing(s -> s.getSpecTuple().makeOrder()));

    return this;
  }

  /**
   * 根据规格组对sku进行分组
   *
   * @return
   */
  public Map<SpecTuple, List<Sku>> groupSkuBySpecTuple() {
    return skus.stream().collect(Collectors.groupingBy(Sku::getSpecTuple));
  }


  public static Map<SkuOperation, List<Sku>> groupSkuByOperation(Spu origin, Spu target){
    val result = new HashMap<SkuOperation, List<Sku>>();

    Set<SpecTuple> originSet = origin.specDefineTuple.cartesianProduct();
    Set<SpecTuple> targetSet = target.specDefineTuple.cartesianProduct();

    val originSkuMap = origin.groupSkuBySpecTuple();
    val targetSkuMap = target.groupSkuBySpecTuple();

    result.put(SkuOperation.DELETE, listSkuBySpecTuples(originSkuMap, Sets.difference(originSet, targetSet)));
    result.put(SkuOperation.CREATE, listSkuBySpecTuples(targetSkuMap, Sets.difference(targetSet, originSet)));
    result.put(SkuOperation.UPDATE_TO, listSkuBySpecTuples(targetSkuMap, Sets.intersection(originSet, targetSet)));
    result.put(SkuOperation.UPDATE_FROM, listSkuBySpecTuples(originSkuMap, Sets.intersection(originSet,targetSet)));

    return result;
  }

  protected static List<Sku> listSkuBySpecTuples(Map<SpecTuple, List<Sku>> skuMap, Set<SpecTuple> tuples) {
    return tuples.stream().map(spec -> skuMap.get(spec).get(0)).collect(Collectors.toList());
  }
  
}
