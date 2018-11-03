package top.zhacker.retail.spu.application.unit.param;

import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.unit.model.UnitStatus;


/**
 * Created by zhacker.
 * Time 2018/10/6 下午9:47
 */
@Data
@Accessors(chain = true)
public class UnitCreateParam {
  
  private ShopId shopId;
  
  private String name;
  
  private UnitStatus status;
}
