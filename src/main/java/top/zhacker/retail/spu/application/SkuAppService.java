package top.zhacker.retail.spu.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.spu.code.CodeLockService;
import top.zhacker.retail.spu.domain.spu.code.SkuBarCodeTuple;
import top.zhacker.retail.spu.application.spu.param.SkuUpdateParam;
import top.zhacker.retail.spu.domain.sku.model.Sku;
import top.zhacker.retail.spu.domain.sku.model.SkuId;
import top.zhacker.retail.spu.domain.sku.SkuRepo;


/**
 * Created by zhacker.
 * Time 2018/8/19 下午1:28
 */
@Service
public class SkuAppService {
  
  @Autowired
  private SkuRepo skuRepo;
  
  @Autowired
  private CodeLockService codeLockService;
  
  public Optional<Sku> findBySkuId(Long shopId, Long skuId){
    return Optional.ofNullable(skuRepo.findBySkuId(new ShopId(shopId), new SkuId(skuId)));
  }
  
  @Transactional
  public void updateSku(SkuUpdateParam param){
    
    this.findBySkuId(param.getShopId(),param.getSkuId())
        .map(sku-> {
          SkuBarCodeTuple skuBarCodeTuple = codeLockService.lockSkuBarCodes(
              new SkuBarCodeTuple(
                sku.getShopId(),sku.getSpuId(),sku.getSkuId(),param.getBarCodes()
              )
          );
          return sku.changeBarCodes(skuBarCodeTuple);
        })
        .map(sku-> sku.changeRetailPrice(param.getRetailPrice()))
        .ifPresent(sku-> skuRepo.updateSku(sku));
  }

}
