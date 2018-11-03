package top.zhacker.retail.spu.domain.spu.spec;

import com.google.common.collect.Sets;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.zhacker.core.model.AssertionConcern;

import static java.util.stream.Collectors.toSet;


/**
 * Created by zhacker.
 * Time 2018/8/17 上午9:49
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public class SpecDefineTuple extends AssertionConcern {
  
  private static final int SpuSpecsMaxLimit = 300;
  
  
  @Size(max = 3, message = "规格定义最多为3个")
  List<SpecDefine> defines = new ArrayList<>();
  
  
  public SpecDefineTuple(List<SpecDefine> defines){
    this.defines = defines;
    this.validate();
  }
  
  
  protected SpecDefineTuple() {
  }
  
  
  @Override
  public void validate(){
    super.validate();
    assertArgumentTrue(cartesianProduct().size() <= SpuSpecsMaxLimit, "规格定义的笛卡尔积数量超过"+SpuSpecsMaxLimit);
  }
  
  public String toJson(){
    return JSON.toJSONString(this);
  }
  
  public Set<SpecTuple> cartesianProduct(){
    return Sets.cartesianProduct(
        IntStream.rangeClosed(0, defines.size()-1)
            .mapToObj(i-> defines.get(i).makeSpecs())
            .collect(Collectors.toList()))
        .stream()
        .map(SpecTuple::new)
        .collect(toSet());
  }
  
  public void fillSpecTupleOrder(List<SpecTuple> tuples){
    
    Map<String, Map<String, String>> orders = new HashMap<>();
    for(int i = 0; i<defines.size(); ++i){
      SpecDefine define = defines.get(i);
      String key = define.getKey();
      for(int j=0; j<define.getValues().size(); ++j ){
        String value = define.getValues().get(j);
        
        if(! orders.containsKey(key)){
          orders.put(key, new HashMap<>());
        }
        orders.get(key).put(value, ""+i*1000+ j + 1);
      }
    }
    
    tuples.forEach(specTuple -> {
      specTuple.getSpecs().forEach(spec->{
        
        String order = Optional.ofNullable(orders.get(spec.getKey()))
            .map(m-> m.get(spec.getValue()))
            .orElse("");
        
        spec.setOrder(order);
        
      });
    });
  }
  
  public boolean cartesianProductEquals(Set<SpecTuple> tuples){
    Set<SpecTuple> product = cartesianProduct();
    return product.size()==tuples.size() && product.containsAll(tuples);
  }
  
}
