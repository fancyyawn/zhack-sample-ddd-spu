package top.zhacker.retail.spu.infra.repo.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

import top.zhacker.retail.spu.domain.spu.Spu;
import top.zhacker.retail.spu.infra.repo.handler.PhotoTupleHandler;
import top.zhacker.retail.spu.infra.repo.handler.SpecDefineTupleHandler;
import top.zhacker.retail.spu.infra.repo.handler.SpuBarCodeTupleHandler;


/**
 * Created by zhacker.
 * Time 2018/8/19 下午5:00
 */
@Mapper
public interface SpuMapper {
  
  @Options(useGeneratedKeys = true, keyProperty = "id")
  @InsertProvider(type = SpuMapper.class, method = "createSql")
  void create(Spu spu);
  
  @Results(
      id = "spuDetail",
      value = {
          @Result(property = "shopId.id", column = "shop_id"),
          @Result(property = "spuId.id", column = "spu_id"),
          @Result(property = "no.code", column = "no"),
          @Result(property = "no.shopId.id", column = "shop_id"),
          @Result(property = "no.spuId.id", column = "spu_id"),
          @Result(property = "barCodes", column = "bar_codes", typeHandler = SpuBarCodeTupleHandler.class),
          @Result(property = "photoTuple", column = "photos", typeHandler = PhotoTupleHandler.class),
          @Result(property = "categoryId.id", column = "category_id"),
          @Result(property = "unitId.id", column = "unit_id"),
          @Result(property = "specDefineTuple", column = "spec_define_tuple", typeHandler = SpecDefineTupleHandler.class)
      }
  )
  @Select("select * from spu where shop_id = #{shopId} and spu_id = #{spuId}")
  Spu findById(@Param("shopId") Long shopId, @Param("spuId") Long spuId);
  
  
  @ResultMap("spuDetail")
  @Select("select * from spu where shop_id=#{shopId}")
  List<Spu> findByShopId(@Param("shopId")Long shopId);
  
  @UpdateProvider(type = SpuMapper.class, method = "updateSql")
  int update(Spu spu);
  
  
  static String createSql(){
    return new SQL()
        .INSERT_INTO("spu")
        .INTO_COLUMNS("shop_id", "spu_id", "name","no","bar_codes","photos","category_id","unit_id","spec_define_tuple")
        .INTO_VALUES("#{shopId.id}","#{spuId.id}", "#{name}", "#{no.code}", "#{barCodes}", "#{photoTuple}", "#{categoryId.id}", "#{unitId.id}", "#{specDefineTuple}")
        .toString();
  }
  
  static String updateSql(){
    return new SQL(){{
      UPDATE("spu");
      SET("name=#{name}");
      SET("photos=#{photoTuple}");
      SET("bar_codes = #{barCodes}");
      SET("category_id = #{categoryId.id}");
      SET("unit_id = #{unitId.id}");
      SET("spec_define_tuple = #{specDefineTuple}");
      WHERE("shop_id=#{shopId.id} and spu_id=#{spuId.id}");
    }}.toString();
  }
}
