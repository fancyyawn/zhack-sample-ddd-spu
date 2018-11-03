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
import top.zhacker.retail.spu.domain.category.model.Category;
import top.zhacker.retail.spu.domain.category.model.CategoryId;
import top.zhacker.retail.spu.domain.category.CategoryRepo;
import top.zhacker.retail.spu.application.CategoryAppService;
import top.zhacker.retail.spu.application.category.param.CategoryCreateParam;
import top.zhacker.retail.spu.application.category.param.CategoryUpdateParam;
import top.zhacker.retail.spu.domain.shop.ShopId;


/**
 * Created by zhacker.
 * Time 2018/8/13 下午4:48
 */
@ParamLog
@RestController
@RequestMapping("/v1/category")
public class CategoryRestApi {
  
  @Autowired
  private CategoryAppService categoryService;
  
  @Autowired
  private CategoryRepo categoryRepo;
  
  @GetMapping("detail")
  public Result<Category> findByShopIdAndId(Long shopId, Long categoryId){
    return Result.ok(categoryRepo.findByShopIdAndId(new ShopId(shopId), new CategoryId(categoryId)));
  }
  
  @GetMapping("count")
  public Result<Long> count(Long shopId){
    return Result.ok(categoryRepo.countByShopId(new ShopId(shopId)));
  }
  
  @GetMapping("list")
  public Result<List<Category>> findByShopId(Long shopId){
    return Result.ok(categoryRepo.findByShopId(new ShopId(shopId)));
  }
  
  @PostMapping("/create")
  public Result<Category> create(@RequestBody CategoryCreateParam param){
    return Result.ok(categoryService.create(param));
  }
  
  @PostMapping("/update")
  public Result<Boolean> update(@RequestBody CategoryUpdateParam param){
    categoryService.update(param);
    return Result.ok(true);
  }
}
