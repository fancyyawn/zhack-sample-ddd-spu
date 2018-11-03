package top.zhacker.retail.spu.gate.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.core.response.Result;
import top.zhacker.retail.spu.application.spu.param.SkuUpdateParam;
import top.zhacker.retail.spu.application.SkuAppService;


/**
 * Created by zhacker.
 * Time 2018/8/19 下午8:06
 */
@ParamLog
@RestController
@RequestMapping("/v1/sku")
public class SkuRestApi {
  
  @Autowired
  private SkuAppService skuService;
  
  @PostMapping("/update")
  public Result<Boolean> updateSku(@RequestBody SkuUpdateParam param){
    skuService.updateSku(param);
    return Result.ok(true);
  }
}
