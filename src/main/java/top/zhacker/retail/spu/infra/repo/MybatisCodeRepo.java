package top.zhacker.retail.spu.infra.repo;

import org.springframework.stereotype.Repository;

import top.zhacker.retail.spu.domain.spu.code.CodeLock;
import top.zhacker.retail.spu.domain.spu.code.CodeLockRepo;


/**
 * Created by zhacker.
 * Time 2018/8/18 上午11:39
 */
@Repository
public class MybatisCodeRepo implements CodeLockRepo {
  
  @Override
  public void remove(CodeLock code) {
  
  }
  
  
  @Override
  public CodeLock tryAcquire(Long shopId, String code) {
    return null;
  }
}
