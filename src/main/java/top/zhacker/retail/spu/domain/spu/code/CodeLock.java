package top.zhacker.retail.spu.domain.spu.code;

import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.spu.SpuId;
import top.zhacker.retail.spu.domain.sku.model.SkuId;


/**
 * Created by zhacker.
 * Time 2018/8/17 下午3:11
 */
@Data
@Accessors(chain = true)
public class CodeLock {
  
  private ShopId shopId;
  
  private String code;
  
  private CodeType codeType;
  
  private SpuId spuId;
  
  private SkuId skuId;
  
  public enum CodeType{
    SPU_NO,
    SPU_BAR,
    SKU_NO,
    SKU_BAR
  }
}
