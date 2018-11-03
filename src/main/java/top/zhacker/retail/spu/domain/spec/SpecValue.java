package top.zhacker.retail.spu.domain.spec;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.zhacker.core.model.IdentifiedEntity;
import top.zhacker.retail.spu.domain.shop.ShopId;


/**
 * Created by zhacker.
 * Time 2018/8/17 上午9:50
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SpecValue extends IdentifiedEntity {
  private ShopId shopId;
  private SpecKey key;
  private String name;
}
