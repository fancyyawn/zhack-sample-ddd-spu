package top.zhacker.retail.spu.application.spu.param;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.retail.spu.domain.spu.photo.PhotoTuple;
import top.zhacker.retail.spu.domain.spu.spec.SpecDefineTuple;


/**
 * Created by chensu on 2018/5/23.
 */
@Data
@Accessors(chain = true)
public class SpuCreateParam {
  
  private Long shopId;

  /**
   * 分类
   */
  @NotNull(message = "商品分类不能为空")
  private Long categoryId;
  
  /**
   * 单位
   */
  @NotNull(message = "单位不能为空")
  private Long unitId;
  /**
   * 名称
   */
  private String name;
  
  /**
   * 图片地址 如不设置 前端会默认传一个默认图片 json格式
   */
  private PhotoTuple photoTuple;

  /**
   * 商品条码
   */
  private String spuNo;

  /**
   * 一品多码
   */
  @Size(max = 10,message = "最多支持10个更多条码")
  private List<String> barCodes = new ArrayList<>();//一品多码
  
  /**
   * 多规格时的规格定义
   */
  private SpecDefineTuple specDefineTuple;

  /**
   * 商品规格保存参数
   */
  @Size(max = 600 , message = "最多支持600个规格")
  private List<SkuCreateParam> skus = new ArrayList<>();
}
