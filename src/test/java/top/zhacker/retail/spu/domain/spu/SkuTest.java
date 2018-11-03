package top.zhacker.retail.spu.domain.spu;

import org.junit.Test;

import java.util.Arrays;

import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.sku.model.Sku;
import top.zhacker.retail.spu.domain.spu.spec.Spec;
import top.zhacker.retail.spu.domain.spu.spec.SpecTuple;


/**
 * Created by zhacker.
 * Time 2018/8/17 下午11:23
 */
public class SkuTest {
  
  @Test
  public void test() throws Exception{
    Sku sku = Sku.builder()
        .shopId(new ShopId(1L))
//        .barCodes(new SkuBarCodeTuple(Arrays.asList(new SkuBarCode().setCode("dasdfsd"))))
        .specTuple(new SpecTuple(Arrays.asList(
            new Spec("size", "")
        )))
        .build();
  }
}
