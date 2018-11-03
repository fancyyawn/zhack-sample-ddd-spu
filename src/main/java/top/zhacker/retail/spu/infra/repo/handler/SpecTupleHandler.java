package top.zhacker.retail.spu.infra.repo.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import top.zhacker.retail.spu.domain.spu.spec.SpecTuple;
import top.zhacker.boot.mybatis.handler.JsonTypeHandler;


/**
 * Created by zhacker.
 * Time 2018/8/19 下午7:43
 */
@MappedTypes(SpecTuple.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class SpecTupleHandler extends JsonTypeHandler<SpecTuple> {
  
}
