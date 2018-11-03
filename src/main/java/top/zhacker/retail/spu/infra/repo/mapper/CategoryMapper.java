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

import top.zhacker.retail.spu.domain.category.model.Category;


/**
 * Created by zhacker.
 * Time 2018/8/13 下午4:02
 */
@Mapper
public interface CategoryMapper {
  
  @Options(useGeneratedKeys = true, keyProperty = "id")
  @Insert("insert into category(shop_id,category_id,name,parent_id,level,score,created_at,updated_at) " +
      "values(#{shopId.id},#{categoryId.id},#{name},#{parentId.id},#{level.level},#{score.score},now(),now())")
  void create(Category category);
  
  @Update("update category set name=#{name}, parent_id=#{parentId.id}, level=#{level.level}, score=#{score.score}, updated_at=now() " +
      "where shop_id=#{shopId.id} and category_id = #{categoryId.id}")
  int update(Category category);
  
  @Update("update category set deleted=1 where category_id = #{categoryId}")
  void delete(@Param("categoryId") Long categoryId);
  
  @Results(id = "categoryResult", value = {
      @Result(id=true, property = "id", column = "id"),
      @Result(property = "shopId.id", column = "shop_id"),
      @Result(property = "categoryId.id", column = "category_id"),
      @Result(property = "parentId.id", column = "parent_id"),
      @Result(property = "name", column = "name"),
      @Result(property = "level.level", column = "level"),
      @Result(property = "score.score", column = "score"),
  })
  @Select("select * from category where shop_id=#{shopId} and category_id=#{categoryId} and deleted=0 limit 1")
  Category findByShopIdAndId(@Param("shopId") Long shopId, @Param("categoryId") Long categoryId);
  
  @ResultMap("categoryResult")
  @Select("select * from category where shop_id=#{shopId} and deleted=0")
  List<Category> findByShopId(@Param("shopId") Long shopId);
  
  @Select("select count(*) from category where shop_id=#{shopId}")
  Long countByShopId(@Param("shopId") Long shopId);
  
  @ResultMap("categoryResult")
  @Select("select * from category where shop_id=#{shopId} and parent_id=#{parentId} and deleted=0")
  List<Category> findByShopIdAndParentId(@Param("shopId") Long shopId, @Param("parentId") Long parentId);
}
