package top.zhacker.retail.spu.domain.category.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/8/13 上午10:42
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryScore {
  
  private Long score;
  
  private boolean before(CategoryScore other){
    return this.score <= other.score;
  }
  
  private boolean after(CategoryScore other){
    return this.score >= other.score;
  }
}
