package top.zhacker.retail.spu.domain.category.event;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.zhacker.core.model.BaseDomainEvent;
import top.zhacker.retail.spu.domain.category.model.CategoryId;
import top.zhacker.retail.spu.domain.shop.ShopId;


/**
 * Created by zhacker.
 * Time 2018/8/13 上午10:52
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CategoryCreatedEvent extends BaseDomainEvent {
  private ShopId shopId;
  private CategoryId categoryId;
  private String name;
}
