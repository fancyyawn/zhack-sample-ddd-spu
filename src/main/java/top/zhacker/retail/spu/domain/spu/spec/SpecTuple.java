package top.zhacker.retail.spu.domain.spu.spec;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zhacker.core.model.AssertionConcern;

import static java.util.stream.Collectors.joining;


/**
 * Created by zhacker.
 * Time 2018/8/17 上午9:58
 */
@Data
public class SpecTuple extends AssertionConcern {
  
  @NotNull
  @Size(max = 3, message = "规格项最多3层")
  private List<Spec> specs = new ArrayList<>();
  
  public SpecTuple(List<Spec> specs) {
    this.specs = specs;
    this.validate();
  }
  
  protected SpecTuple(){
  
  }
  
  public String makeOrder(){
    return specs.stream().map(Spec::getOrder).collect(joining("_"));
  }
  
}
