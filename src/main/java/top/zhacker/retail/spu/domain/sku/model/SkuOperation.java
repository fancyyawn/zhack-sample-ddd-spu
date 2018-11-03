package top.zhacker.retail.spu.domain.sku.model;

import java.util.EnumSet;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.Getter;


/**
 * Created by zhacker.
 * Time 2018/5/29 下午5:02
 */
public enum SkuOperation {
  CREATE(0, "创建"),
  UPDATE_TO(1, "更新成"),
  UPDATE_FROM(2, "更新源"),
  DELETE(3, "删除");
  
  @Getter
  private final int value;
  @Getter
  private final String name;
  
  SkuOperation(int value, String name) {
    this.value = value;
    this.name = name;
  }
  
  public static Optional<SkuOperation> valueOf(int val) {
    return Stream.of(values()).filter(s -> s.getValue() == val).findFirst();
  }
  
  public static boolean absent(int val){
    return ! valueOf(val).isPresent();
  }
  public static boolean contains(EnumSet<SkuOperation> types, int val){
    return valueOf(val).filter(types::contains).isPresent();
  }
  
}
