package top.zhacker.retail.spu.infra.repo.handler;

import org.apache.ibatis.type.MappedTypes;

import java.util.List;

import top.zhacker.boot.mybatis.handler.ListJsonTypeHandler;
import top.zhacker.retail.spu.domain.sales.SkuSale;


/**
 * Created by zhacker.
 * Time 2018/10/6 下午4:48
 */
@MappedTypes(List.class)
public class SkuSalesListHandler extends ListJsonTypeHandler<SkuSale> {
  
}
