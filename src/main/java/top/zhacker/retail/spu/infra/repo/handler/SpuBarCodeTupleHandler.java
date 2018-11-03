package top.zhacker.retail.spu.infra.repo.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import top.zhacker.retail.spu.domain.spu.code.SpuBarCodeTuple;
import top.zhacker.boot.mybatis.handler.JsonTypeHandler;


/**
 * Created by zhacker.
 * Time 2018/8/19 下午6:10
 */
@MappedTypes(SpuBarCodeTuple.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class SpuBarCodeTupleHandler extends JsonTypeHandler<SpuBarCodeTuple> {
  
}
