package top.zhacker.retail.spu.infra.repo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

import top.zhacker.retail.spu.domain.unit.model.Unit;


/**
 * Created by zhacker.
 * Time 2018/8/19 下午4:54
 */
@Mapper
public interface UnitMapper {
  
  @Options(useGeneratedKeys = true)
  @Insert("insert into unit(shop_id,unit_id,name,status) values (#{shopId.id},#{unitId.id},#{name},#{status});")
  void create(Unit unit);
  
  @Update("update unit set name=#{name}, status=#{status} where shop_id=#{shopId.id} and unit_id=#{unitId.id}")
  void update(Unit unit);
  
  @Results(
      id= "unitDetail",
      value = {
        @Result(property = "shopId.id", column = "shop_id"),
        @Result(property = "unitId.id", column = "unit_id")
      }
  )
  @Select("select * from unit where shop_id=#{shopId} and unit_id=#{unitId}")
  Unit findById(@Param("shopId") Long shopId, @Param("unitId") Long unitId);
  
  
  @ResultMap("unitDetail")
  @Select("select * from unit where shop_id=#{shopId}")
  List<Unit> findByShopId(Long shopId);
}
