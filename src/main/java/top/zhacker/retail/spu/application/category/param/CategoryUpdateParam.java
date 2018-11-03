package top.zhacker.retail.spu.application.category.param;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2017/12/29 下午5:17
 */
@Data
@Accessors(chain = true)
public class CategoryUpdateParam {
  /**
   * 唯一性主键
   */
  @NotNull(message = "分类的ID不能为空")
  private Long id;
  
  @NotNull(message = "商家ID不能为空")
  private Long shopId;
  
  /**
   * 待更改的名称
   */
  private String name;
  
  /**
   * 移动到的目标分类, 当不变更父分类时则传空
   */
  private Long parentId;
}
