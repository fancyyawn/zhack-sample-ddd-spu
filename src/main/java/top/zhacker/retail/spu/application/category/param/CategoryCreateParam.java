package top.zhacker.retail.spu.application.category.param;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2017/12/29 下午5:10
 */
@Data
@Accessors(chain = true)
public class CategoryCreateParam{
  
  @NotNull(message = "商家ID不能为空")
  private Long shopId;
  
  @NotNull(message = "父分类的ID不能为空")
  private Long parentId;
  
  @NotNull(message = "分类的名称不能为空")
  private String name;
}
