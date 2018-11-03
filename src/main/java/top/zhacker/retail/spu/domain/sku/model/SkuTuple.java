package top.zhacker.retail.spu.domain.sku.model;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.val;
import top.zhacker.boot.validate.ListDistinct;
import top.zhacker.core.model.AssertionConcern;
import top.zhacker.retail.spu.domain.spu.spec.SpecDefineTuple;
import top.zhacker.retail.spu.domain.spu.spec.SpecTuple;


/**
 * Created by zhacker.
 * Time 2018/8/18 下午12:26
 */
@Deprecated
@Getter
@EqualsAndHashCode
@ToString
public class SkuTuple extends AssertionConcern {
  
  @NotNull(message = "规格定义不能为空")
  private SpecDefineTuple specDefineTuple;
  
  @ListDistinct(message = "规格不能重复")
  @Size(max = 600, message = "规格数最大不能超过600")
  private List<Sku> skus = new ArrayList<>();
  
  public SkuTuple(SpecDefineTuple specDefineTuple, List<Sku> skus) {
    this.specDefineTuple = specDefineTuple;
    this.skus = skus;
    this.validate();
  }
  
  @Override
  public void validate() {
    super.validate();
    val skuGroups = groupSkuBySpecTuple();
    assertArgumentTrue(skuGroups.values().stream().allMatch(list->list.size()<=1), "存在重复的规格组");
    assertArgumentTrue(specDefineTuple.cartesianProductEquals(skuGroups.keySet()), "与规格定义中的笛卡尔积不相符");
  }
  
  /**
   * 根据规格组排序
   * @return
   */
  public SkuTuple sortSkuBySpecDefineTuple() {
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
  
  
  
  public static Map<SkuOperation, List<Sku>> groupSkuByOperation(SkuTuple origin, SkuTuple target){
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
