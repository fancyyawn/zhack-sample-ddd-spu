package top.zhacker.retail.spu.application.category.param;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 移动只能平级
 *
 * Created by zhacker.
 * Time 2017/11/1 上午9:58
 */
@Data
@Accessors(chain = true)
public class CategoryChangeOrderParam {
  
  @NotNull(message = "商家ID不能为空")
  private Long shopId;
  /**
   * 父分类，当为一级分类时传0
   */
  @NotNull(message = "父分类不能为空")
  private Long parentId;
  
  /**
   * 待移动的分类
   */
  @NotNull(message = "待移动的分类不能为空")
  private Long sourceId;
  
  /**
   * 移动的目标分类ID
   */
  @NotNull(message = "目标分类不能为空")
  private Long targetId;
  
  /**
   * 相对于源分类的位移：向上移动时为-1， 向下移动时为1
   */
  @NotNull(message = "源分类的位移不能为空")
  private Integer sourceOffset;
}
