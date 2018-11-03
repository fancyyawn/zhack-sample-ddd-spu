package top.zhacker.retail.spu.infra.repo.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

import top.zhacker.retail.spu.domain.sku.model.Sku;
import top.zhacker.retail.spu.infra.repo.handler.SkuBarCodeTupleHandler;
import top.zhacker.retail.spu.infra.repo.handler.SpecTupleHandler;


/**
 * Created by zhacker.
 * Time 2018/8/19 下午5:00
 */
@Mapper
public interface SkuMapper {
//
//  void batchCreate(List<Sku> skus);
  
  @Options(useGeneratedKeys = true)
  @InsertProvider(type = SkuMapper.class, method = "createSql")
  void create(Sku sku);
  
  @ResultMap("skuDetail")
  @Select("select * from sku where shop_id=#{shopId} and spu_id=#{spuId}")
  List<Sku> findBySpuId(@Param("shopId") Long shopId,@Param("spuId") Long spuId);
  
  @Results(
      id = "skuDetail",
      value = {
          @Result(property = "shopId.id", column = "shop_id"),
          @Result(property = "skuId.id", column = "sku_id"),
          @Result(property = "spuId.id", column = "spu_id"),
          @Result(property = "no.code", column = "no"),
          @Result(property = "no.shopId.id", column = "shop_id"),
          @Result(property = "no.spuId.id", column = "spu_id"),
          @Result(property = "no.skuId.id", column = "sku_id"),
          @Result(property = "barCodes", column = "bar_codes", typeHandler = SkuBarCodeTupleHandler.class),
          @Result(property = "specTuple", column = "spec_tuple", typeHandler = SpecTupleHandler.class),
      }
  )
  @Select("select * from sku where shop_id=#{shopId} and sku_id=#{skuId}")
  Sku findBySkuId(@Param("shopId") Long shopId,@Param("skuId")  Long skuId);
  
  @Update("update sku set bar_codes=#{barCodes}, retail_price=#{retailPrice} where shop_id=#{shopId.id} and sku_id=#{skuId.id}")
  void update(Sku sku);
  
  static String createSql(Sku sku){
    return new SQL()
        .INSERT_INTO("sku")
        .INTO_COLUMNS("shop_id","spu_id","sku_id","spec_tuple","no","bar_codes","retail_price")
        .INTO_VALUES("#{shopId.id}", "#{spuId.id}", "#{skuId.id}", "#{specTuple}", "#{no.code}", "#{barCodes}", "#{retailPrice}")
        .toString();
  }
}
