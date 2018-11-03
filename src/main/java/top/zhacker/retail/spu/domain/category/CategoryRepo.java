package top.zhacker.retail.spu.domain.category;

import java.util.List;

import top.zhacker.retail.spu.domain.category.model.Category;
import top.zhacker.retail.spu.domain.category.model.CategoryId;
import top.zhacker.retail.spu.domain.shop.ShopId;


/**
 * Created by zhacker.
 * Time 2018/8/13 上午10:13
 */
public interface CategoryRepo {
  
  CategoryId nextId();
  
  void save(Category category);
  
  void delete(CategoryId id);
  
  List<Category> findByShopId(ShopId shopId);
  
  Long countByShopId(ShopId shopId);
  
  Category findByShopIdAndId(ShopId shopId, CategoryId id);
  
  List<Category> findByShopIdAndParentId(ShopId shopId, CategoryId parentId);
}
