package top.zhacker.retail.spu.gate.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.core.response.Result;
import top.zhacker.retail.spu.domain.spu.Spu;
import top.zhacker.retail.spu.application.SpuAppService;
import top.zhacker.retail.spu.application.spu.param.SpuAddSpecParam;
import top.zhacker.retail.spu.application.spu.param.SpuCreateParam;
import top.zhacker.retail.spu.application.spu.param.SpuUpdateParam;


/**
 * Created by zhacker.
 * Time 2018/8/19 下午4:50
 */
@ParamLog
@RestController
@RequestMapping("/v1/spu")
public class SpuRestApi {
  
  @Autowired
  private SpuAppService spuService;
  
  
  @PostMapping("/create")
  public Result<Long> create(@RequestBody SpuCreateParam param){
    return Result.ok(spuService.save(param).getId());
  }
  
  
  /**
   *
   * @param param
   * @return
   */
  @PostMapping("/updateBasic")
  public Result<Boolean> update(@RequestBody SpuUpdateParam param){
    spuService.updateBasic(param);
    return Result.ok(true);
  }
  
  @PostMapping("/addSpecs")
  public Result<Boolean> addSpecs(@RequestBody SpuAddSpecParam param){
    spuService.addSpecs(param);
    return Result.ok(true);
  }
  
  @GetMapping("/detail")
  public Result<Spu> findSpuById(Long shopId, Long spuId){
    
    Optional<Spu> spu = spuService.findSpuById(shopId,spuId)
        .map(Spu::loadCategory)
        .map(Spu::loadUnit);
    
    return Result.ok(spu.orElse(null));
  }
  
  @GetMapping("/list")
  public Result<List<Spu>> listSpus(Long shopId){
    return Result.ok(spuService.list(shopId).take(20).toList().blockingGet());
  }
  
}
