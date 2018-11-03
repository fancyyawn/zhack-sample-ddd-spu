package top.zhacker.retail.spu.infra.repo.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import top.zhacker.boot.mybatis.handler.JsonTypeHandler;
import top.zhacker.retail.spu.domain.spu.photo.PhotoTuple;


/**
 * Created by zhacker.
 * Time 2018/8/19 下午6:11
 */
@MappedTypes(PhotoTuple.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class PhotoTupleHandler extends JsonTypeHandler<PhotoTuple> {
  
}
