package top.zhacker.retail.spu.domain.sku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhacker.retail.spu.domain.shop.ShopId;
import top.zhacker.retail.spu.domain.sku.model.Sku;
import top.zhacker.retail.spu.domain.sku.model.SkuId;
import top.zhacker.retail.spu.domain.spu.SpuId;
import top.zhacker.retail.spu.domain.spu.code.CodeLockService;
import top.zhacker.retail.spu.domain.spu.code.SkuBarCodeTuple;
import top.zhacker.retail.spu.domain.spu.code.SkuNo;
import top.zhacker.retail.spu.application.spu.param.SkuCreateParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhacker.
 * Time 2018/11/1 下午1:13
 */
@Service
public class SkuService {

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private CodeLockService codeLockService;

    //延迟初始化sku,让其他校验都执行完
    public List<Sku> buildSkus(ShopId shopId, SpuId spuId, List<SkuCreateParam> param) {
        return convertSkuBuilders(shopId, spuId, param).stream().map(Sku.SkuBuilder::build).collect(Collectors.toList());
    }

    private List<Sku.SkuBuilder> convertSkuBuilders(ShopId shopId, SpuId spuId, List<SkuCreateParam> param) {
        return param.stream().map(s -> {
            SkuId skuId = skuRepo.nextId();
            return Sku.builder()
                    .shopId(shopId)
                    .spuId(spuId)
                    .skuId(skuId)
                    .specTuple(s.getSpecTuple())
                    .no(codeLockService.lockSkuNo(new SkuNo(shopId, spuId, skuId, s.getSkuNo())))
                    .barCodes(codeLockService.lockSkuBarCodes(new SkuBarCodeTuple(shopId, spuId, skuId, s.getBarCodes())))
                    .retailPrice(s.getRetailPrice());
        }).collect(Collectors.toList());
    }
}
