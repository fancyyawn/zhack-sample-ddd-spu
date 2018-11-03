package top.zhacker.retail.spu.domain.spu;

import java.util.List;

import top.zhacker.retail.spu.domain.shop.ShopId;


/**
 * Created by zhacker.
 * Time 2018/8/17 下午3:43
 */
public interface SpuRepo {
  
  List<Spu> findByShopId(ShopId shopId);
  
  SpuId nextId();
  
  void save(Spu spu);
  
  Spu findById(ShopId shopId, SpuId spuId);
  
  int updateBarCodes(Spu spu);
  
  int updateBasic(Spu spu);
  
  int updateCategory(Spu spu);
  
  int updateName(Spu spu);
  
  int updatePhotoTuple(Spu spu);
  
  int updateUnit(Spu spu);
  
  void addSpecs(Spu spu);
}
