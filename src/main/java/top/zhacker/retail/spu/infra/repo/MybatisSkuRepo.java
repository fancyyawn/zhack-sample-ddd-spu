package top.zhacker.retail.spu.infra.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import top.zhacker.boot.idgen.IdGen;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.spu.SpuId;
import top.zhacker.retail.spu.domain.sku.model.Sku;
import top.zhacker.retail.spu.domain.sku.model.SkuId;
import top.zhacker.retail.spu.domain.sku.SkuRepo;
import top.zhacker.retail.spu.infra.repo.mapper.SkuMapper;


/**
 * Created by zhacker.
 * Time 2018/8/18 上午11:39
 */
@Repository
public class MybatisSkuRepo implements SkuRepo {
  
  @Autowired
  private IdGen idGen;
  
  @Autowired
  private SkuMapper skuMapper;
  
  @Override
  public void save(Sku sku) {
    skuMapper.create(sku);
  }
  
  
  @Override
  public void batchSave(List<Sku> skus) {
    skus.forEach(sku-> skuMapper.create(sku));
//    skuMapper.batchCreate(skus);
  }
  
  
  @Override
  public List<Sku> findBySpuId(ShopId shopId, SpuId spuId) {
    return skuMapper.findBySpuId(shopId.getId(), spuId.getId());
  }
  
  
  @Override
  public Sku findBySkuId(ShopId shopId, SkuId skuId) {
    return skuMapper.findBySkuId(shopId.getId(), skuId.getId());
  }
  
  
  @Override
  public SkuId nextId() {
    return new SkuId(idGen.generateId());
  }
  
  
  @Override
  public void updateSku(Sku sku) {
    skuMapper.update(sku);
  }
}
