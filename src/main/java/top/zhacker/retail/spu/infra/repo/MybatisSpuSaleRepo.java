package top.zhacker.retail.spu.infra.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import top.zhacker.boot.idgen.IdGen;
import top.zhacker.retail.spu.domain.sales.SpuSale;
import top.zhacker.retail.spu.domain.sales.SpuSaleRepo;
import top.zhacker.retail.spu.infra.repo.mapper.SpuSaleMapper;


/**
 * Created by zhacker.
 * Time 2018/10/6 下午4:49
 */
@Repository
public class MybatisSpuSaleRepo implements SpuSaleRepo {
  
  @Autowired
  private SpuSaleMapper spuSaleMapper;
  
  @Autowired
  private IdGen idGen;
  
  
  @Override
  public Long nextId() {
    return idGen.generateId();
  }
  
  
  @Override
  public void save(SpuSale sale) {
    spuSaleMapper.create(sale);
  }
  
  @Override
  public SpuSale findById(Long shopId, Long saleId) {
    return spuSaleMapper.findById(shopId,saleId);
  }
}
