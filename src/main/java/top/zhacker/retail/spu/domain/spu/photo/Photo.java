package top.zhacker.retail.spu.domain.spu.photo;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import top.zhacker.core.model.AssertionConcern;


/**
 * Created by zhacker.
 * Time 2018/8/17 下午3:04
 */
@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo extends AssertionConcern {
  
  @Size(min=1, max = 150, message = "url长度在1-150")
  @Pattern(message = "URL格式不正常", regexp = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\/])+(\\\\?{0,1}(([A-Za-z0-9-~]+\\\\={0,1})([A-Za-z0-9-~]*)\\\\&{0,1})*)$")
  private String url;
  
  public Photo(String url) {
    this.url = url;
    this.validate();
  }
  
}
