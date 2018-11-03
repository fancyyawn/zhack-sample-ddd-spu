package top.zhacker.retail.spu.infra.repo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import top.zhacker.retail.spu.domain.sales.SpuSale;
import top.zhacker.retail.spu.infra.repo.handler.SkuSalesListHandler;


/**
 * Created by zhacker.
 * Time 2018/10/6 下午4:36
 */
@Mapper
public interface SpuSaleMapper {
  
  @Options(useGeneratedKeys = true, keyProperty = "id")
  @Insert("insert into " +
      " sale(shop_id,sale_id,sale_channel,spu_id, sku_sales,version,created_at,updated_at) " +
      " values(#{shopId},#{saleId},#{saleChannel},#{spuId},#{skuSales},#{version},#{createdAt},#{updatedAt})")
  void create(SpuSale sale);
  
  @Results(
      id="SpuSaleDetail",
      value = {
          @Result(property = "skuSales", column = "sku_sales", typeHandler = SkuSalesListHandler.class)
      }
  )
  @Select("select * from sale where shop_id=#{shopId} and sale_id=#{saleId}")
  SpuSale findById(@Param("shopId") Long shopId, @Param("saleId") Long saleId);
  
  
}
