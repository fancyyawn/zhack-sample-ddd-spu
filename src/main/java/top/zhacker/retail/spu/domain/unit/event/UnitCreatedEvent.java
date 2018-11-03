package top.zhacker.retail.spu.domain.unit.event;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.zhacker.core.model.BaseDomainEvent;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.unit.model.UnitId;
import top.zhacker.retail.spu.domain.unit.model.UnitStatus;


/**
 * Created by zhacker.
 * Time 2018/8/25 下午8:01
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
public class UnitCreatedEvent extends BaseDomainEvent {
  
  private ShopId shopId;
  
  private UnitId unitId;
  
  private String name;
  
  private UnitStatus status;
}
