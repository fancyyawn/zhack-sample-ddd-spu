package top.zhacker.retail.spu.domain.spu.photo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import top.zhacker.core.model.AssertionConcern;


/**
 * Created by zhacker.
 * Time 2018/8/17 下午3:04
 */
@EqualsAndHashCode
@ToString
@Getter
public class PhotoTuple extends AssertionConcern {
  
  @NotNull(message = "图片列表不能为空")
  @Size(max = 10, min = 1, message = "图片列表数量必须在1-10")
  private List<Photo> photos = new ArrayList<>();
  
  public PhotoTuple(List<Photo> photos) {
    this.photos = photos;
    this.validate();
  }
  
  
  protected PhotoTuple() {
  }
  
  
  /**
   * 获取主图
   *
   * @return
   */
  private Photo getMainPhoto(){
    if(photos.isEmpty()){
      return null;
    }
    return photos.get(0);
  }
}
