package top.zhacker.retail.spu.infra.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import top.zhacker.boot.idgen.IdGen;
import top.zhacker.retail.spu.domain.category.model.Category;
import top.zhacker.retail.spu.domain.category.model.CategoryId;
import top.zhacker.retail.spu.domain.category.CategoryRepo;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.infra.repo.mapper.CategoryMapper;


/**
 * Created by zhacker.
 * Time 2018/8/13 下午3:08
 */
@Repository
public class MybatisCategoryRepo implements CategoryRepo {
  
  @Autowired
  private CategoryMapper mapper;
  
  @Autowired
  private IdGen idGen;
  
  @Override
  public CategoryId nextId() {
    return new CategoryId(idGen.generateId());
  }
  
  @Override
  public void save(Category category) {
    if(category.getId()==-1){
      mapper.create(category);
    }else{
      mapper.update(category);
    }
  }
  
  @Override
  public void delete(CategoryId id) {
    mapper.delete(id.getId());
  }
  
  @Override
  public List<Category> findByShopId(ShopId shopId) {
    return mapper.findByShopId(shopId.getId());
  }
  
  
  @Override
  public Long countByShopId(ShopId shopId) {
    return mapper.countByShopId(shopId.getId());
  }
  
  
  @Override
  public Category findByShopIdAndId(ShopId shopId, CategoryId id) {
    return mapper.findByShopIdAndId(shopId.getId(),id.getId());
  }
  
  
  @Override
  public List<Category> findByShopIdAndParentId(ShopId shopId, CategoryId parentId) {
    return mapper.findByShopIdAndParentId(shopId.getId(),parentId.getId());
  }
}
