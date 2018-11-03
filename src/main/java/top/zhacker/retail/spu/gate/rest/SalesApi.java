package top.zhacker.retail.spu.gate.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.core.response.Result;
import top.zhacker.retail.spu.application.SalesAppService;
import top.zhacker.retail.spu.domain.sales.SpuSale;
import top.zhacker.retail.spu.application.sales.param.SpuSaleCreateParam;


/**
 * Created by zhacker.
 * Time 2018/10/6 下午4:56
 */
@ParamLog
@RestController
@RequestMapping("/v1/sales")
public class SalesApi {
  
  @Autowired
  private SalesAppService salesService;
  
  @GetMapping("")
  public Result<SpuSale> findById(Long shopId, Long saleId){
    return Result.ok(salesService.findById(shopId,saleId));
  }
  
  @PostMapping("")
  public Result<Long> sell(@RequestBody SpuSaleCreateParam param){
    return Result.ok(salesService.sell(param));
  }
}
