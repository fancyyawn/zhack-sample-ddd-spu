package top.zhacker.retail.spu.domain.sku.model;

import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import top.zhacker.boot.event.publish.DomainEventPublisher;
import top.zhacker.core.model.IdentifiedEntity;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.spu.SpuId;
import top.zhacker.retail.spu.domain.spu.code.SkuBarCodeTuple;
import top.zhacker.retail.spu.domain.spu.code.SkuNo;
import top.zhacker.retail.spu.domain.sku.event.SkuCreatedEvent;
import top.zhacker.retail.spu.domain.spu.spec.SpecTuple;


/**
 * Created by zhacker.
 * Time 2018/8/17 上午10:33
 */
@Data
@Accessors(chain = true)
@Builder
@Slf4j
public class Sku extends IdentifiedEntity {
  
  @NotNull(message = "商家不能为空")
  private ShopId shopId;
  @NotNull(message = "spuID不能为空")
  private SpuId spuId;
  @NotNull(message = "skuID不能为空")
  private SkuId skuId;
  @NotNull(message = "规格组不能为空")
  private SpecTuple specTuple;
  @NotNull(message = "条码不能为空")
  private SkuNo no;
  @NotNull(message = "条码列表不能为空")
  private SkuBarCodeTuple barCodes;
  @NotNull(message = "建议零售价不能为空")
  @Max(value = 999999900, message = "建议零售价最大为9999999元")
  @Min(value = 1, message = "建议零售价最小为0.01元")
  private Long retailPrice;
  
  protected Sku() {
  }
  
  public Sku(ShopId shopId, SpuId spuId, SkuId skuId, SpecTuple specTuple, SkuNo no, SkuBarCodeTuple barCodes, Long retailPrice) {
    this.shopId = shopId;
    this.spuId = spuId;
    this.skuId = skuId;
    this.specTuple = specTuple;
    this.no = no;
    this.barCodes = barCodes;
    this.retailPrice = retailPrice;
    this.validate();
    DomainEventPublisher.publish(new SkuCreatedEvent());
  }
  
//  public Sku changeSpecTuple(SpecTuple specTuple){
//
//    if(specTuple==null){
//      return this;
//    }
//
//    if(Objects.equals(specTuple, this.specTuple)){
//      return this;
//    }
//
//    this.specTuple = specTuple;
//
//    return this;
//  }
  
  public Sku changeBarCodes(SkuBarCodeTuple tuple){
    if(tuple==null){
      return this;
    }
    if(Objects.equals(tuple, this.barCodes)){
      return this;
    }
    
    this.barCodes = tuple;
    
    return this;
  }
  
  public Sku changeRetailPrice(Long retailPrice){
    
    if(retailPrice==null){
      return this;
    }
    
    new Sku().setRetailPrice(retailPrice).validate("retailPrice");
    
    if(Objects.equals(this.retailPrice, retailPrice)){
      return this;
    }
    
    this.retailPrice = retailPrice;
    
    return this;
  }
  
}
