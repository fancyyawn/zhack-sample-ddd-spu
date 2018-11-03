package top.zhacker.retail.spu.domain.spu.code;

/**
 * Created by zhacker.
 * Time 2018/8/17 下午3:20
 */
public interface CodeLockRepo {
  
  void remove(CodeLock code);
  
  CodeLock tryAcquire(Long shopId, String code);
}
