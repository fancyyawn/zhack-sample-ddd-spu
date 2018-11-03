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
import top.zhacker.core.model.AssertionConcern;
import top.zhacker.boot.validate.ListDistinct;
import top.zhacker.boot.validate.ListStringSize;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.spu.SpuId;


/**
 * Created by zhacker.
 * Time 2018/8/18 上午10:40
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public class SpuBarCodeTuple extends AssertionConcern {
  
  @NotNull(message = "商家不能为空")
  private ShopId shopId;
  @NotNull(message = "spuID不能为空")
  private SpuId spuId;
  
  @NotNull(message = "条码列表不能为空")
  @ListStringSize(max = 20, message = "条码最多20个字符")
  @ListDistinct(message = "条码列表不能重复")
  @Size(max = 10, min = 0, message = "最多支持10个条码")
  List<String> codes = new ArrayList<>();
  
  public SpuBarCodeTuple(ShopId shopId, SpuId spuId, List<String> codes) {
    this.codes = codes;
    this.shopId = shopId;
    this.spuId = spuId;
    this.validate();
  }
  
  protected SpuBarCodeTuple() {
  }
}
