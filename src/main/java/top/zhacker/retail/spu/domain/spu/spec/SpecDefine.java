package top.zhacker.retail.spu.domain.spu.spec;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.zhacker.boot.validate.ListDistinct;
import top.zhacker.boot.validate.ListStringSize;
import top.zhacker.core.model.AssertionConcern;

import static java.util.stream.Collectors.toSet;


/**
 * Created by zhacker.
 * Time 2018/8/17 下午12:06
 */
@Data
@Accessors(chain = true)
public class SpecDefine extends AssertionConcern {
  
  @NotNull(message = "规格定义项不能为空")
  @Size(max = 20, min=1, message = "规格定义项字符数必须为1-20")
  private String key;
  
  @ListDistinct(message = "规格定义值列表存在重复数据")
  @ListStringSize(min = 1, max = 20, message = "规格定义项字符数必须为1-20")
  @NotNull(message = "规格定义值列表不能为空")
  private List<String> values = new ArrayList<>();
  
  
  public SpecDefine(String key, List<String> values) {
    this.key = key;
    this.values = values;
    this.validate();
  }
  
  
  protected SpecDefine() {
  }
  
  
  public Set<Spec> makeSpecs() {
    return IntStream.rangeClosed(0, values.size() - 1).mapToObj(i -> new Spec(key, values.get(i))).collect(toSet());
  }
  
}
