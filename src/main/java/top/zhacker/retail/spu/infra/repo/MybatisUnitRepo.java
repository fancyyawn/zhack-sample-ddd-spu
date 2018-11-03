package top.zhacker.retail.spu.infra.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import top.zhacker.boot.idgen.IdGen;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.unit.model.Unit;
import top.zhacker.retail.spu.domain.unit.model.UnitId;
import top.zhacker.retail.spu.domain.unit.UnitRepo;
import top.zhacker.retail.spu.infra.repo.mapper.UnitMapper;


/**
 * Created by zhacker.
 * Time 2018/8/18 上午11:41
 */
@Repository
public class MybatisUnitRepo implements UnitRepo {
  
  @Autowired
  private UnitMapper unitMapper;
  
  @Autowired
  private IdGen idGen;
  
  @Override
  public UnitId nextId() {
    return new UnitId(idGen.generateId());
  }
  
  @Override
  public void save(Unit unit) {
    unitMapper.create(unit);
  }
  
  @Override
  public Unit findById(ShopId shopId, UnitId unitId) {
    return unitMapper.findById(shopId.getId(), unitId.getId());
  }
  
  @Override
  public List<Unit> list(ShopId shopId) {
    return unitMapper.findByShopId(shopId.getId());
  }
}
