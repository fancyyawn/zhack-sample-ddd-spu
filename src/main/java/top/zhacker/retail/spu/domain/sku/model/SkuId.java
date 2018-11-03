package top.zhacker.retail.spu.domain.sku.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Created by zhacker.
 * Time 2018/8/17 下午3:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SkuId {
  private Long id;
}
