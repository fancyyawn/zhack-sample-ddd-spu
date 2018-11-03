package top.zhacker.retail.spu.domain.shop;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Created by zhacker.
 * Time 2018/8/13 上午10:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopId implements Serializable {
  private Long id;
}
