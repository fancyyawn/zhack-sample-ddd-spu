package top.zhacker.retail.spu.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zhacker.core.exception.BusinessException;
import top.zhacker.retail.spu.domain.sales.SpuSale;
import top.zhacker.retail.spu.domain.sales.SpuSaleRepo;
import top.zhacker.retail.spu.application.sales.param.SpuSaleCreateParam;
import top.zhacker.retail.spu.domain.spu.Spu;


/**
 * Created by zhacker.
 * Time 2018/10/6 下午3:48
 */
@Service
public class SalesAppService {
  
  @Autowired
  private SpuSaleRepo spuSaleRepo;
  
  @Autowired
  private SpuAppService spuService;
  
  
  public SpuSale findById(Long shopId, Long saleId) {
    return spuSaleRepo.findById(shopId,saleId);
  }
  
  
  public Long sell(SpuSaleCreateParam param){
    
    Spu spu = spuService.findSpuById(param.getShopId(), param.getSpuId()).orElseThrow(()-> new BusinessException("spu不存在"));
    
    Long saleId = spuSaleRepo.nextId();
    
    SpuSale spuSale = new SpuSale(
        param.getShopId(),
        saleId,
        param.getSaleChannel(),
        param.getSpuId()
    );
    
    param.getSkuSales().forEach(skuSaleCreateParam ->
        spuSale.addSku(skuSaleCreateParam.getSkuId(), skuSaleCreateParam.getPrice()));
    
    spuSaleRepo.save(spuSale);
    
    return saleId;
  }
  
}
