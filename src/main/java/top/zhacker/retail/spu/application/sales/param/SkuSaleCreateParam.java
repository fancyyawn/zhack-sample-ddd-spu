package top.zhacker.retail.spu.application.sales.param;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/10/6 下午3:47
 */
@Data
@Accessors(chain = true)
public class SkuSaleCreateParam {
  private Long skuId;
  private Long price;
}
