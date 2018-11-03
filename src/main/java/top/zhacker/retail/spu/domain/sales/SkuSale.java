package top.zhacker.retail.spu.domain.sales;

import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.core.model.IdentifiedEntity;


/**
 * Created by zhacker.
 * Time 2018/10/6 下午3:37
 */
@Data
@Accessors(chain = true)
public class SkuSale extends IdentifiedEntity {
  private Long shopId;
  private Long saleId;
  private SaleChannel saleChannel;
  private Long spuId;
  private Long skuId;
  private Long price;
  
  private SkuSale(){}
  
  
  public SkuSale(SpuSale spuSale, Long skuId, Long price) {
    this.shopId = spuSale.getShopId();
    this.saleId = spuSale.getSaleId();
    this.saleChannel = spuSale.getSaleChannel();
    this.spuId = spuSale.getSpuId();
    
    this.skuId = skuId;
    this.price = price;
  }
}
