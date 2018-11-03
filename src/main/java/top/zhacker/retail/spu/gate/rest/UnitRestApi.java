package top.zhacker.retail.spu.gate.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.core.response.Result;
import top.zhacker.retail.spu.domain.unit.model.Unit;
import top.zhacker.retail.spu.application.UnitAppService;
import top.zhacker.retail.spu.application.unit.param.UnitCreateParam;


/**
 * Created by zhacker.
 * Time 2018/10/6 下午9:54
 */
@ParamLog
@RestController
@RequestMapping("/v1/unit")
public class UnitRestApi {
  
  @Autowired
  private UnitAppService unitService;
  
  @GetMapping
  public Result<Unit> findById(Long shopId, Long unitId){
    return Result.ok(unitService.findById(shopId,unitId));
  }
  
  @GetMapping("list")
  public Result<List<Unit>> list(Long shopId){
    return Result.ok(unitService.list(shopId));
  }
  
  @PostMapping
  public Result<Long> create(@RequestBody UnitCreateParam param){
    return Result.ok(unitService.create(param));
  }
}
