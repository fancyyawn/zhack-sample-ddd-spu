package top.zhacker.retail.spu.domain.spu.event;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.zhacker.core.model.BaseDomainEvent;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.spu.SpuId;
import top.zhacker.retail.spu.domain.sku.model.SkuId;
import top.zhacker.retail.spu.domain.spu.spec.SpecDefineTuple;


/**
 * Created by zhacker.
 * Time 2018/8/19 上午10:07
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SpuAddSpecEvent extends BaseDomainEvent {
  private ShopId shopId;
  private SpuId spuId;
  private SpecDefineTuple origin;
  private SpecDefineTuple target;
  private List<SkuId> skusAdded;
}
