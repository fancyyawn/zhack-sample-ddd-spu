package top.zhacker.retail.spu.domain.spu.code;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.zhacker.boot.validate.ListDistinct;
import top.zhacker.boot.validate.ListStringSize;
import top.zhacker.core.model.AssertionConcern;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.spu.SpuId;
import top.zhacker.retail.spu.domain.sku.model.SkuId;


/**
 * Created by zhacker.
 * Time 2018/8/18 上午11:21
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public class SkuBarCodeTuple extends AssertionConcern {
  
  @NotNull(message = "商家不能为空")
  private ShopId shopId;
  @NotNull(message = "spuID不能为空")
  private SpuId spuId;
  @NotNull(message = "skuID不能为空")
  private SkuId skuId;
  
  @NotNull(message = "条码列表不能为空")
  @ListDistinct(message = "条码列表不能重复")
  @ListStringSize(max = 20, message = "最多20个字符")
  @Size(max = 10, min = 0, message = "最多支持10个条码")
  List<String> codes = new ArrayList<>();
  
  public SkuBarCodeTuple(ShopId shopId, SpuId spuId, SkuId skuId, List<String> codes) {
    this.codes = codes;
    this.shopId = shopId;
    this.spuId = spuId;
    this.skuId = skuId;
    this.validate();
  }
  
  protected SkuBarCodeTuple() {
  }
  
  public static SkuBarCodeTuple from(ShopId shopId, SpuId spuId, SkuId skuId, List<String> codes){
    return new SkuBarCodeTuple(shopId,spuId,skuId,codes);
  }
}
