package top.zhacker.retail.spu.domain.spu.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.zhacker.core.model.BaseDomainEvent;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.spu.SpuId;
import top.zhacker.retail.spu.domain.spu.photo.PhotoTuple;


/**
 * Created by zhacker.
 * Time 2018/8/19 上午9:44
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SpuPhotoChangedEvent extends BaseDomainEvent {
  private ShopId shopId;
  private SpuId spuId;
  private PhotoTuple origin;
  private PhotoTuple target;
}
