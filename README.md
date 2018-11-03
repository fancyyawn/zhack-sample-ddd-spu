# 商品子域DDD示例

* gate: 对外暴露的服务
    * rest：对外暴露的rest服务
    * dubbo：对外暴露的dubbo服务
    * listener: 消息处理器
* application：应用服务层
* domain：领域层
    * shop: 店铺
    * category: 分类
    * unit: 单位
    * spec: 规格
    * spu: 商品库
    * sales: 上架销售
* infra：基础设施层
    * mq: 发消息实现
    * proxy：代理实现
    * repo：仓储实现