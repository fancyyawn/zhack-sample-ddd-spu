package top.zhacker.retail.spu.domain.spu.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.zhacker.core.model.BaseDomainEvent;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.spu.SpuId;


/**
 * Created by zhacker.
 * Time 2018/8/18 上午10:28
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SpuCreatedEvent extends BaseDomainEvent {
  private ShopId shopId;
  private SpuId spuId;
}
