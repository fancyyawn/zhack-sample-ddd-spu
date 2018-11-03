package top.zhacker.retail.spu.domain.unit;

import java.util.List;

import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.unit.model.Unit;
import top.zhacker.retail.spu.domain.unit.model.UnitId;


/**
 * Created by zhacker.
 * Time 2018/8/17 下午3:43
 */
public interface UnitRepo {
  UnitId nextId();
  void save(Unit unit);
  Unit findById(ShopId shopId, UnitId unitId);
  List<Unit> list(ShopId shopId);
}
