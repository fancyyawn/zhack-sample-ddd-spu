package top.zhacker.retail.spu.domain.category.model;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.zhacker.core.model.AssertionConcern;


/**
 * Created by zhacker.
 * Time 2018/8/13 上午10:28
 */
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryLevel extends AssertionConcern implements Serializable {
  
  public static final int MIN_LEVEL = 1;
  
  public static final int MAX_LEVEL = 4;
  
  public static final Range<Integer> validRange = Range.range(MIN_LEVEL, BoundType.CLOSED, MAX_LEVEL, BoundType.CLOSED);
  
  @NotNull(message = "分类层次不能为空")
  @Min(value = MIN_LEVEL, message = "分类层次超出范围[1-4]")
  @Max(value = MAX_LEVEL, message = "分类层次超出范围[1-4]")
  @Getter
  private Integer level;
  
  public CategoryLevel(Integer level) {
    this.level = level;
    super.validate();
  }
  
  public CategoryLevel move(Integer step){
    Integer targetLevel = level + step;
    assertArgumentTrue(validRange.contains(targetLevel), "分类层次超出范围[1-4]");
    return new CategoryLevel(targetLevel);
  }
  
  public CategoryLevel nextLevel(){
    return move(1);
  }
  
  public CategoryLevel preLevel(){
    return move(-1);
  }
  
  public static CategoryLevel minLevel(){
    return new CategoryLevel(MIN_LEVEL);
  }
  
  public static CategoryLevel maxLevel(){
    return new CategoryLevel(MAX_LEVEL);
  }
  
  public static CategoryLevel rootLevel(){
    CategoryLevel level = new CategoryLevel();
    level.level = 0;
    return level;
  }
  
  
}
