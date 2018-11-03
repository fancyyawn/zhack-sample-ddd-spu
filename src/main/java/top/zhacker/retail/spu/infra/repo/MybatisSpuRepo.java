package top.zhacker.retail.spu.infra.repo;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.zhacker.boot.idgen.IdGen;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.spu.Spu;
import top.zhacker.retail.spu.domain.spu.SpuId;
import top.zhacker.retail.spu.domain.spu.SpuRepo;
import top.zhacker.retail.spu.infra.repo.mapper.SkuMapper;
import top.zhacker.retail.spu.infra.repo.mapper.SpuMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Created by zhacker.
 * Time 2018/8/18 上午11:40
 */
@Repository
public class MybatisSpuRepo implements SpuRepo {
  
  @Autowired
  private IdGen idGen;
  
  @Autowired
  private SpuMapper spuMapper;
  
  @Autowired
  private SkuMapper skuMapper;
  
  @Override
  public List<Spu> findByShopId(ShopId shopId) {
    return spuMapper.findByShopId(shopId.getId());
  }
  
  @Override
  public SpuId nextId() {
    return new SpuId(idGen.generateId());
  }
  
  @Override
  public void save(Spu spu) {
    spuMapper.create(spu);
    spu.getSkus().forEach(sku -> skuMapper.create(sku));
  }
  
  @Override
  public Spu findById(ShopId shopId, SpuId spuId) {
    return spuMapper.findById(shopId.getId(), spuId.getId());
  }
  
  @Override
  public int updateBasic(Spu spu) {
    return spuMapper.update(spu);
  }
  
  @Override
  public int updateBarCodes(Spu spu) {
    return spuMapper.update(spu);
  }
  
  
  @Override
  public int updateCategory(Spu spu) {
    return spuMapper.update(spu);
  }
  
  
  @Override
  public int updateName(Spu spu) {
    return spuMapper.update(spu);
  }
  
  
  @Override
  public int updatePhotoTuple(Spu spu) {
    return spuMapper.update(spu);
  }
  
  
  @Override
  public int updateUnit(Spu spu) {
    return spuMapper.update(spu);
  }
  
  @Override
  public void addSpecs(Spu spu) {
    spuMapper.update(spu);
    val groups = spu.getSkus().stream().collect(Collectors.partitioningBy(s-> Objects.equals(-1L, s.getId())));
    if(! groups.get(true).isEmpty()) {
      groups.get(true).forEach(sku->skuMapper.create(sku));
    }
  }
  
  
}
