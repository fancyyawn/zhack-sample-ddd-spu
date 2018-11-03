package top.zhacker.retail.spu.domain.spu.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.zhacker.core.model.BaseDomainEvent;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.spu.SpuId;
import top.zhacker.retail.spu.domain.unit.model.UnitId;


/**
 * Created by zhacker.
 * Time 2018/8/18 下午4:58
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SpuUnitChangedEvent extends BaseDomainEvent {
  private ShopId shopId;
  private SpuId spuId;
  private UnitId origin;
  private UnitId target;
}
