package top.zhacker.retail.spu.domain.spec;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.zhacker.core.model.IdentifiedEntity;
import top.zhacker.retail.spu.domain.shop.ShopId;


/**
 * Created by zhacker.
 * Time 2018/8/17 上午9:49
 */
@Getter
@Setter(AccessLevel.PACKAGE)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SpecKey extends IdentifiedEntity {
  private ShopId shopId;
  private String name;
}
