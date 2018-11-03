package top.zhacker.retail.spu.domain.category.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.zhacker.boot.registry.DomainRegistry;
import top.zhacker.core.model.IdentifiedEntity;
import top.zhacker.boot.event.publish.DomainEventPublisher;
import top.zhacker.retail.spu.domain.category.CategoryRepo;
import top.zhacker.retail.spu.domain.category.event.CategoryCreatedEvent;
import top.zhacker.retail.spu.domain.category.event.CategoryNameChangedEvent;
import top.zhacker.retail.spu.domain.category.event.CategoryParentChangedEvent;
import top.zhacker.retail.spu.domain.shop.ShopId;

import static java.util.stream.Collectors.groupingBy;


/**
 * Created by zhacker.
 * Time 2018/8/13 上午10:13
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"parent", "children", "ancestors", "descendants"})
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public class Category extends IdentifiedEntity {
  
  
  public final static Long MAX=500L;
  
  @NotNull(message = "商户ID不能为空")
  private ShopId shopId;
  
  @NotNull(message = "分类ID不能为空")
  private CategoryId categoryId;
  
  @NotNull(message = "名称不能为空")
  @Size(min = 1, max = 20, message = "分类名称字符数在1-20之间")
  private String name;
  
  @NotNull(message = "父分类不能为空")
  private CategoryId parentId;
  
  @NotNull(message = "分类层级不能为空")
  private CategoryLevel level;
  
  @NotNull(message = "分类排分不能为空")
  private CategoryScore score;
  
  
  private Date createdAt;
  
  private Date updatedAt;
  
  
  /***********导航属性**************/

  @Setter(AccessLevel.PUBLIC)
  private transient Category parent; //父分类
  @Setter(AccessLevel.PUBLIC)
  private transient List<Category> children = new ArrayList<>(); //所有直接孩子分类
  @Setter(AccessLevel.PUBLIC)
  private transient List<Category> ancestors = new ArrayList<>(); //所有祖先分类
  @Setter(AccessLevel.PUBLIC)
  private transient List<Category> descendants = new ArrayList<>(); //所有后代分类
  
  Category() {
  }
  
  /**
   * 用于创建一级分类
   *
   * @param shopId
   * @param categoryId
   * @param name
   */
  public Category(ShopId shopId, CategoryId categoryId, String name) {
    this(shopId, categoryId, name, Category.root(shopId));
  }
  
  /**
   * 分类创建入口
   *
   * @param shopId
   * @param categoryId
   * @param name
   * @param parent
   */
  public Category(ShopId shopId, CategoryId categoryId, String name, Category parent) {
    this.shopId = shopId;
    this.categoryId = categoryId;
    this.name = name;
    this.level = parent.getLevel().nextLevel();
    this.score = new CategoryScore(categoryId.getId());
    this.parentId = parent.getCategoryId();
    this.parent = parent;
    this.createdAt = new Date();
    this.updatedAt = new Date();
    
    super.validate();
  
    DomainEventPublisher.publish(new CategoryCreatedEvent(shopId,categoryId,name));
  }
  
  
  /**
   * 获取根分类
   *
   * @param shopId
   * @return
   */
  public static Category root(ShopId shopId){
    Category root = new Category();
    root.shopId =shopId;
    root.categoryId = CategoryId.root();
    root.name = "root";
    root.parent = null;
    root.parentId = null;
    root.level = CategoryLevel.rootLevel();
    root.score = new CategoryScore(0L);
    return root;
  }
  
  
  /**
   * 是否为根分类
   *
   * @return
   */
  public boolean isRoot(){
    return Objects.equals(categoryId, CategoryId.root());
  }
  
  
  /**
   * 更改名称
   *
   * @param name
   */
  public void changeName(String name){
    
    validate(new Category().setName(name), "name");
    
    DomainEventPublisher.publish(new CategoryNameChangedEvent(shopId,categoryId,this.name,name));
    
    this.name = name;
  }
  
  
  /**
   * 预先校验能否变更父分类
   *
   * @param parent
   */
  public void changeParentPreCheck(Category parent){
    assertArgumentNotNull(parent, "新父分类不能为空");
    assertArgumentNotNull(this.parent, "旧父分类不能为空");
    assertArgumentFalse(inDescendants(parent), "父分类不能为其子分类");
    
    //校验分类层次是否在范围内
    parent.level.nextLevel();
    this.getDescendants().forEach(c-> c.getLevel().move(parent.level.getLevel() - this.parent.level.getLevel()));
  }
  
  
  /**
   * 变更父分类
   *
   * @param parent
   */
  public void changeParent(Category parent){
    
    changeParentPreCheck(parent);
    
    Integer levelChanged =  parent.level.getLevel() - this.parent.level.getLevel();
  
    this.parentId = parent.getCategoryId();
    this.parent = parent;
    this.level = parent.level.nextLevel();
    
    this.getDescendants().forEach(c-> c.setLevel(c.getLevel().move(levelChanged)));
    
    DomainEventPublisher.publish(new CategoryParentChangedEvent(shopId,categoryId,this.parent.getCategoryId(),parent.getCategoryId(), levelChanged));
  }
  
  
  /**
   * 是否在子分类中
   *
   * @param category
   * @return
   */
  public boolean inDescendants(Category category){
    return getDescendants().stream().anyMatch(c-> c.getCategoryId().equals(category.getCategoryId()));
  }
  
  
  /**
   * 是否为一级分类
   *
   * @return
   */
  public boolean inFirstLevel(){
    return Objects.equals(this.getParentId(), CategoryId.root());
  }
  
  public Category loadParent(){
    if(this.getParentId().equals(CategoryId.root())){
      this.parent =  Category.root(this.getShopId());
    }else {
      this.parent = DomainRegistry.repo(CategoryRepo.class).findByShopIdAndId(this.getShopId(), this.getParentId());
    }
    return this;
  }
  
  /**
   * 加载所有孩子
   *
   * @return
   */
  public Category loadChildren(){
    if(! this.getChildren().isEmpty()){
      return this;
    }
    List<Category> children = DomainRegistry.repo(CategoryRepo.class).findByShopIdAndParentId(this.getShopId(), this.getCategoryId());
    this.setChildren(children);
    
    return this;
  }
  
  /**
   * 加载所有后代结点
   *
   * @return
   */
  public Category loadDescendants(){
    if(! this.getChildren().isEmpty()){
      return this; //如果已经加载过就不加载了
    }
    this.setDescendants(findDescendants(this));
    return this;
  }
  
  private List<Category> findDescendants(Category category) {
    
    List<Category> categories = DomainRegistry.repo(CategoryRepo.class).findByShopId(category.getShopId());
    Map<Long, List<Category>> categoryGroupByParentId = categories.stream().collect(groupingBy(c-> c.getParentId().getId()));
    
    List<Category> children = new ArrayList<>();
    findChildrenLevelByLevel(category.getCategoryId().getId(), categoryGroupByParentId, category.getLevel().getLevel(), CategoryLevel.MAX_LEVEL, children);
    
    return children;
  }
  
  private void findChildrenLevelByLevel(Long categoryId, Map<Long, List<Category>> categoryGroupByParentId, int curLevel, int maxLevel, List<Category> result) {
    if (curLevel < maxLevel && categoryGroupByParentId.containsKey(categoryId)) {
      List<Category> children = categoryGroupByParentId.get(categoryId);
      if (children == null) {
        return;
      }
      result.addAll(children);
      for (Category category : children) {
        findChildrenLevelByLevel(category.getCategoryId().getId(), categoryGroupByParentId, curLevel + 1, maxLevel, result);
      }
    }
  }
  
  
  /**
   * 加载所有祖先
   *
   * @return
   */
  public Category loadAncestors(){
    this.setAncestors(findAncestors(this));
    return this;
  }
  
  
  /**
   *
   * @param current
   * @return
   */
  protected List<Category> findAncestors(Category current){
    
    Map<Long, Category> map =
        DomainRegistry.repo(CategoryRepo.class)
            .findByShopId(this.getShopId()).stream()
            .collect(Collectors.toMap(Category::getId, Function.identity()));
    
    List<Category> paths = new ArrayList<>();
    
    Long parentId = current.getParentId().getId();
    while(parentId!=null && parentId > 0){
      Category parent = map.get(parentId);
      if(parent==null){
        break;
      }
      paths.add(parent);
      parentId = parent.getParentId().getId();
    }
    return paths;
  }
  
}
