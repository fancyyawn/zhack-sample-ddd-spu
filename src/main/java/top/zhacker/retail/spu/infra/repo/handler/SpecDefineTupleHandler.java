package top.zhacker.retail.spu.infra.repo.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import top.zhacker.retail.spu.domain.spu.spec.SpecDefineTuple;
import top.zhacker.boot.mybatis.handler.JsonTypeHandler;


/**
 * Created by zhacker.
 * Time 2018/8/19 下午6:12
 */
@MappedTypes(SpecDefineTuple.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class SpecDefineTupleHandler extends JsonTypeHandler<SpecDefineTuple> {
  
}
