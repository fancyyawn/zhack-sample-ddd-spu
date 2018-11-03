package top.zhacker.retail.spu.domain.spu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/8/17 上午10:33
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SpuId {
  private Long id;
}
