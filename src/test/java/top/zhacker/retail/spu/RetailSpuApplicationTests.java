package top.zhacker.retail.spu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;
import top.zhacker.retail.spu.domain.category.model.Category;
import top.zhacker.retail.spu.domain.category.model.CategoryId;
import top.zhacker.retail.spu.domain.category.CategoryRepo;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.spu.SpuId;
import top.zhacker.retail.spu.domain.spu.SpuRepo;
import top.zhacker.retail.spu.application.SpuAppService;
import top.zhacker.retail.spu.application.spu.param.SkuCreateParam;
import top.zhacker.retail.spu.application.spu.param.SpuCreateParam;
import top.zhacker.retail.spu.domain.spu.photo.Photo;
import top.zhacker.retail.spu.domain.spu.photo.PhotoTuple;
import top.zhacker.retail.spu.domain.spu.spec.Spec;
import top.zhacker.retail.spu.domain.spu.spec.SpecDefine;
import top.zhacker.retail.spu.domain.spu.spec.SpecDefineTuple;
import top.zhacker.retail.spu.domain.spu.spec.SpecTuple;
import top.zhacker.retail.spu.domain.unit.model.Unit;
import top.zhacker.retail.spu.domain.unit.model.UnitId;
import top.zhacker.retail.spu.domain.unit.UnitRepo;
import top.zhacker.retail.spu.domain.unit.model.UnitStatus;

import static org.mockito.ArgumentMatchers.argThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RetailSpuApplicationTests {
  
  @Autowired
  private SpuAppService spuService;
  
  @MockBean
  private SpuRepo spuRepo;
//
//  @MockBean
//  private SkuRepo skuRepo;
  
  @MockBean
  private CategoryRepo categoryRepo;
  
  @MockBean
  private UnitRepo unitRepo;
  
//  @MockBean
//  private CodeLockService codeService;
  
  @Before
  public void init(){
    ShopId shopId = new ShopId(1L);
    CategoryId categoryId = new CategoryId(1L);
    UnitId unitId = new UnitId(1L);
    Mockito.when(categoryRepo.findByShopIdAndId(shopId, categoryId)).thenReturn(
        new Category(shopId, categoryId, "A")
    );
    Mockito.when(unitRepo.findById(shopId,unitId)).thenReturn(new Unit(shopId, unitId, "unit", UnitStatus.OPEN));
  
    AtomicLong spuIdGen = new AtomicLong(0L);
    Mockito.when(spuRepo.nextId()).thenReturn(new SpuId(spuIdGen.addAndGet(1L)));

//    AtomicLong skuIdGen = new AtomicLong(0L);
//    Mockito.when(skuRepo.nextId()).thenReturn(new SkuId(skuIdGen.addAndGet(1L)));
//
  }
  
  @Test
  public void test_spu_save() {
    SpuCreateParam param = new SpuCreateParam()
        .setShopId(1L)
        .setCategoryId(1L)
        .setUnitId(1L)
        .setSpuNo("spuNo")
        .setBarCodes(Arrays.asList("spuBarCode"))
        .setName("spu")
        .setPhotoTuple(new PhotoTuple(Arrays.asList(new Photo("http://baidu.com"))))
        .setSpecDefineTuple(new SpecDefineTuple(Arrays.asList(
            new SpecDefine("color", Arrays.asList("red", "green"))
        )))
        .setSkus(Arrays.asList(
            new SkuCreateParam()
                .setSkuNo("skuNo1")
                .setBarCodes(Arrays.asList("skuBarCode1"))
                .setSpecTuple(new SpecTuple(Arrays.asList(new Spec("color","red"))))
                .setRetailPrice(10L)
            ,new SkuCreateParam()
                .setSkuNo("skuNo2")
                .setBarCodes(Arrays.asList("skuBarCode2"))
                .setSpecTuple(new SpecTuple(Arrays.asList(new Spec("color","green"))))
                .setRetailPrice(999999L)
        ));
    
    
    SpuId spuId = spuService.save(param);
    
    Mockito.verify(spuRepo).save(argThat(a->{
      return true;
    }));
  }
  
  @Test
  public void test() throws Exception{
  
  }

}
