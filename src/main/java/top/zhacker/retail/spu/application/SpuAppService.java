package top.zhacker.retail.spu.application;

import io.reactivex.Observable;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.retail.spu.application.spu.param.SpuAddSpecParam;
import top.zhacker.retail.spu.application.spu.param.SpuCreateParam;
import top.zhacker.retail.spu.application.spu.param.SpuUpdateParam;
import top.zhacker.retail.spu.domain.category.model.Category;
import top.zhacker.retail.spu.domain.category.model.CategoryId;
import top.zhacker.retail.spu.domain.category.CategoryRepo;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.sku.SkuService;
import top.zhacker.retail.spu.domain.spu.Spu;
import top.zhacker.retail.spu.domain.spu.SpuId;
import top.zhacker.retail.spu.domain.spu.SpuRepo;
import top.zhacker.retail.spu.domain.spu.code.CodeLockService;
import top.zhacker.retail.spu.domain.spu.code.SpuBarCodeTuple;
import top.zhacker.retail.spu.domain.spu.code.SpuNo;
import top.zhacker.retail.spu.domain.spu.photo.PhotoTuple;
import top.zhacker.retail.spu.domain.unit.model.Unit;
import top.zhacker.retail.spu.domain.unit.model.UnitId;

import java.util.List;
import java.util.Optional;


/**
 * Created by zhacker.
 * Time 2018/8/17 下午3:30
 */
@Service
public class SpuAppService {
  
  @Autowired
  private SpuRepo spuRepo;
  
  @Autowired
  private CategoryAppService categoryService;
  
  @Autowired
  private CategoryRepo categoryRepo;
  
  @Autowired
  private UnitAppService unitService;
  
  @Autowired
  private CodeLockService codeLockService;
  
  @Autowired
  private SkuService skuService;
  
  @Transactional
  public SpuId save(SpuCreateParam param){
    
    ShopId shopId = new ShopId(param.getShopId());
    Category category = categoryService.findById(param.getShopId(), param.getCategoryId());
    Unit unit  = unitService.findById(param.getShopId(), param.getUnitId());
    
    SpuId spuId = spuRepo.nextId();
    
    SpuNo spuNo = codeLockService.lockSpuNo(new SpuNo(shopId, spuId, param.getSpuNo()));
    
    SpuBarCodeTuple spuBarCodeTuple = codeLockService.lockSpuBarCodes(new SpuBarCodeTuple(shopId,spuId,param.getBarCodes()));
    
    val skus = skuService.buildSkus(shopId, spuId, param.getSkus());
    
    Spu spu = Spu.builder()
        .shopId(shopId)
        .spuId(spuId)
        .no(spuNo)
        .barCodes(spuBarCodeTuple)
        .name(param.getName())
        .photoTuple(param.getPhotoTuple())
        .category(category)
        .unit(unit)
        .specDefineTuple(param.getSpecDefineTuple())
        .skus(skus)
        .build();
    
    spuRepo.save(spu);
    
    return spuId;
  }
  
  public Observable<Spu> list(Long shopId){
    return Observable.fromCallable(()->{
      return spuRepo.findByShopId(new ShopId(shopId));
    }).flatMap(Observable::fromIterable);
  }
  
  public Optional<Spu> findSpuById(Long shopId, Long spuId){
    return findSpuBasicById(new ShopId(shopId), new SpuId(spuId))
        .map(Spu::loadSkuTuple);
  }
  
  Optional<Spu> findSpuBasicById(ShopId shopId, SpuId spuId){
    return Optional.ofNullable(spuRepo.findById(shopId,spuId));
  }
  
  @Transactional
  public void changeName(ShopId shopId, SpuId spuId, String name){
    this.findSpuBasicById(shopId,spuId)
        .map(spu-> spu.changeName(name))
        .ifPresent(spu-> spuRepo.updateName(spu));
  }
  
  
  @Transactional
  public void changeCategory(ShopId shopId, SpuId spuId, CategoryId categoryId){
    Category category = categoryRepo.findByShopIdAndId(shopId,categoryId);
    this.findSpuBasicById(shopId,spuId)
        .map(spu-> spu.changeCategory(category))
        .ifPresent(spu-> spuRepo.updateCategory(spu));
  }
  
  @Transactional
  public void changeUnit(ShopId shopId, SpuId spuId, UnitId unitId){
    Unit unit = unitService.findById(shopId.getId(),unitId.getId());
    this.findSpuBasicById(shopId,spuId)
        .map(spu-> spu.changeUnit(unit))
        .ifPresent(spu-> spuRepo.updateUnit(spu));
  }
  
  @Transactional
  public void changeBarCodes(ShopId shopId, SpuId spuId, List<String> barCodes){
    SpuBarCodeTuple tuple = codeLockService.lockSpuBarCodes(new SpuBarCodeTuple(shopId,spuId,barCodes));
    this.findSpuBasicById(shopId,spuId)
        .map(spu-> spu.changeBarCodes(tuple))
        .ifPresent(spu-> spuRepo.updateBarCodes(spu));
  }
  
  @Transactional
  public void changePhotoTuple(ShopId shopId, SpuId spuId, PhotoTuple tuple){
    this.findSpuBasicById(shopId, spuId)
        .map(spu-> spu.changePhotoTuple(tuple))
        .ifPresent(spu-> spuRepo.updatePhotoTuple(spu));
  }
  
  @Transactional
  public void updateBasic(SpuUpdateParam param){
    
    ShopId shopId = new ShopId(param.getShopId());
    SpuId spuId = new SpuId(param.getSpuId());
    Category category = categoryService.findById(param.getShopId(), param.getCategoryId());
    Unit unit  = unitService.findById(param.getShopId(), param.getUnitId());
  
    SpuBarCodeTuple spuBarCodeTuple = codeLockService.lockSpuBarCodes(new SpuBarCodeTuple(shopId,spuId,param.getBarCodes()));
  
    this.findSpuBasicById(shopId, spuId)
        .map(spu-> spu.changeName(param.getName()))
        .map(spu-> spu.changeBarCodes(spuBarCodeTuple))
        .map(spu-> spu.changeUnit(unit))
        .map(spu-> spu.changeCategory(category))
        .map(spu-> spu.changePhotoTuple(param.getPhotoTuple()))
        .ifPresent(spu-> spuRepo.updateBasic(spu));
  }
  
  @Transactional
  public void addSpecs(SpuAddSpecParam param){
    
    ShopId shopId = new ShopId(param.getShopId());
    SpuId spuId = new SpuId(param.getSpuId());
    
    Spu spu = this
        .findSpuBasicById(new ShopId(param.getShopId()), new SpuId(param.getSpuId()))
        .orElseThrow(()-> new BusinessException("spu.not.exist"));
    
    spu.addSpecs(param.getSpecDefineTuple(), skuService.buildSkus(shopId, spuId, param.getSkus()));
    
    spuRepo.addSpecs(spu);
  }
}
