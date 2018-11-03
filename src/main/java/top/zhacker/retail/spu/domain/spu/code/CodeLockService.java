package top.zhacker.retail.spu.domain.spu.code;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import top.zhacker.core.exception.BusinessException;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.spu.code.SkuBarCodeTuple;
import top.zhacker.retail.spu.domain.spu.code.SkuNo;
import top.zhacker.retail.spu.domain.spu.code.SpuBarCodeTuple;
import top.zhacker.retail.spu.domain.spu.code.SpuNo;


/**
 * Created by zhacker.
 * Time 2018/8/17 下午3:58
 */
@Service
public class CodeLockService {
  
  Map<String, Object> codesDB = new ConcurrentHashMap<>();
  
  public SpuNo lockSpuNo(SpuNo spuNo){
//    if(codesDB.containsKey(spuNo.getCode())){
//      throw new BusinessException(String.format("编码[%s]已经被占用", spuNo.getCode()));
//    }
//    codesDB.put(spuNo.getCode(), spuNo);
    return spuNo;
  }
  
  public SpuBarCodeTuple lockSpuBarCodes(SpuBarCodeTuple tuple){
//    tuple.getBarCodes().forEach(code->{
//      if(codesDB.containsKey(code.getCode())){
//        throw new BusinessException(String.format("编码[%s]已经被占用", code.getCode()));
//      }
//      codesDB.put(code.getCode(), code);
//    });
    return tuple;
  }
  
  public SkuNo lockSkuNo(SkuNo skuNo){
//    if(codesDB.containsKey(skuNo.getCode())){
//      throw new BusinessException(String.format("编码[%s]已经被占用", skuNo.getCode()));
//    }
//    codesDB.put(skuNo.getCode(), skuNo);
    return skuNo;
  }
  
  public SkuBarCodeTuple lockSkuBarCodes(SkuBarCodeTuple tuple){
//    tuple.getBarCodes().forEach(code->{
//      if(codesDB.containsKey(code.getCode())){
//        throw new BusinessException(String.format("编码[%s]已经被占用", code.getCode()));
//      }
//      codesDB.put(code.getCode(), code);
//    });
    return tuple;
  }
}
