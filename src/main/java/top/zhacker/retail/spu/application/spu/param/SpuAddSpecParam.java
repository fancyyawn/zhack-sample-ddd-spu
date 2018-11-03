package top.zhacker.retail.spu.application.spu.param;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.retail.spu.domain.spu.spec.SpecDefineTuple;


/**
 * Created by zhacker.
 * Time 2018/8/18 下午9:54
 */
@Data
@Accessors(chain = true)
public class SpuAddSpecParam {
  
  private Long shopId;
  
  private Long spuId;
  
  private SpecDefineTuple specDefineTuple;
  
  private List<SkuCreateParam> skus = new ArrayList<>();
  
}
