package top.zhacker.retail.spu.application.spu.param;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/8/19 下午1:21
 */
@Data
@Accessors(chain = true)
public class SkuUpdateParam implements SkuSaveParam {
  
  /**
   * 店铺ID
   */
  private Long shopId;
  
  /**
   * 规格id
   */
  private Long skuId;
  /**
   * 建议零售价
   */
  private Long retailPrice;
  
  /**
   * 一品多码
   */
  private List<String> barCodes;//一品多码
  
  /**
   * 外部编码
   */
  private List<String> outCodes;

}
