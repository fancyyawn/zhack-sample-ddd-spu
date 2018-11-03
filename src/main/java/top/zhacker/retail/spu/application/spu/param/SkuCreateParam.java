package top.zhacker.retail.spu.application.spu.param;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.retail.spu.domain.spu.spec.SpecTuple;


/**
 * Created by chensu on 2018/5/23.
 */
@Data
@Accessors(chain = true)
public class SkuCreateParam implements SkuSaveParam {
  
  /**
   * 店铺ID
   */
  private Long shopId;
  
  /**
   * 建议零售价
   */
  private Long retailPrice;
  /**
   * 商品编码
   */
  private String skuNo;

  /**
   * 一品多码
   */
  private List<String> barCodes;//一品多码
  
  /**
   *规格属性
   */
  private SpecTuple specTuple;
  
}

