package top.zhacker.retail.spu.domain.spu;

import com.google.gson.Gson;

import com.alibaba.fastjson.JSON;

import org.junit.Test;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import top.zhacker.retail.spu.domain.spu.spec.Spec;
import top.zhacker.retail.spu.domain.spu.spec.SpecTuple;

/**
 * Created by zhacker.
 * Time 2018/10/6 下午6:04
 */
@Slf4j
public class SpecTupleTest {
  
  @Test
  public void test() throws Exception{
    val spec = new SpecTuple(Arrays.asList(new Spec("color", "red"), new Spec("size", "2")));
    String json = JSON.toJSONString(spec);
    log.info("{}", JSON.parseObject(json, SpecTuple.class));
  }
  
  @Test
  public void test_Spec() throws Exception{
    log.info("{}", new Gson().fromJson( new Gson().toJson(new Spec("size", "2").setOrder("1")), Spec.class));
  }
}
