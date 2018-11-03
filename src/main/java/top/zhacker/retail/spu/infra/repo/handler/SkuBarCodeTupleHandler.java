package top.zhacker.retail.spu.infra.repo.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import top.zhacker.retail.spu.domain.spu.code.SkuBarCodeTuple;
import top.zhacker.boot.mybatis.handler.JsonTypeHandler;


/**
 * Created by zhacker.
 * Time 2018/8/19 下午7:40
 */
@MappedTypes(SkuBarCodeTuple.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class SkuBarCodeTupleHandler extends JsonTypeHandler<SkuBarCodeTuple> {
  
}
