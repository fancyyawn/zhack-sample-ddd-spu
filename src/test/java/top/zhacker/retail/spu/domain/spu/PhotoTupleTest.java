package top.zhacker.retail.spu.domain.spu;

import org.junit.Test;

import java.util.Arrays;

import top.zhacker.retail.spu.domain.spu.photo.Photo;
import top.zhacker.retail.spu.domain.spu.photo.PhotoTuple;


/**
 * Created by zhacker.
 * Time 2018/8/17 下午11:15
 */
public class PhotoTupleTest {
  
  @Test
  public void test() throws Exception{
    PhotoTuple photoTuple = new PhotoTuple(Arrays.asList(
        new Photo("http://adfasdf")
    ));
  }
  
}
