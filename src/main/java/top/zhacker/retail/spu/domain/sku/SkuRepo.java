package top.zhacker.retail.spu.domain.sku;

import java.util.List;

import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.sku.model.Sku;
import top.zhacker.retail.spu.domain.sku.model.SkuId;
import top.zhacker.retail.spu.domain.spu.SpuId;


/**
 * Created by zhacker.
 * Time 2018/8/17 下午5:33
 */
public interface SkuRepo {
  
  void save(Sku sku);
  
  void batchSave(List<Sku> skus);
  
  List<Sku> findBySpuId(ShopId shopId, SpuId spuId);
  
  Sku findBySkuId(ShopId shopId, SkuId skuId);
  
  SkuId nextId();
  
  void updateSku(Sku sku);
}
