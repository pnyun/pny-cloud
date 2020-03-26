# pny-cloud
#### 项目介绍



#### 软件架构
##### 服务鉴权
通过`JWT`的方式来加强服务之间调度的权限验证，保证内部服务的安全性。
##### 监控
利用Spring Boot Admin 来监控各个独立Server的运行状态；利用Hystrix Dashboard来实时查看接口的运行状态和调用频率等。
##### 负载均衡
将服务保留的rest进行代理和网关控制，除了平常经常使用的node.js、nginx外，Spring Cloud系列的zuul和ribbon，可以帮我们进行正常的网关管控和负载均衡。其中扩展和借鉴国外项目的扩展基于JWT的`Zuul限流插件`，方面进行限流。
##### 服务注册与调用
基于Eureka来实现的服务注册与调用，在Spring Cloud中使用Feign, 我们可以做到使用HTTP请求远程服务时能与调用本地方法一样的编码体验，开发者完全感知不到这是
远程方法，更感知不到这是个HTTP请求。
##### 熔断机制
因为采取了服务的分布，为了避免服务之间的调用“雪崩”，采用了`Hystrix`的作为熔断器，避免了服务之间的“雪崩”。

------
### 系统架构
![输入图片说明]()
### 组织结构
``` lua
pny-cloud
├── pny-eureka -- eureka服务注册中心
├── pny-config -- 配置服务 
├── pny-common -- 公共包
|    ├── pny-common-util -- 工具包
|    ├── pny-common-domain -- domain，包含所有domain实体bean
|    └── pny-common-core -- 核心底层
├── pny-server -- 微服务
|    ├── pny-server-commons -- 通用服务模块
|    └── pny-server-upms -- 用户权限服务模块
├── pny-client -- 应用服务接口层
|    ├── pny-client-admin -- 运营管理系统接口
└── pny-demo -- 示例模块(包含一些示例代码等)
```
#### 项目启动说明
1. 启动 pny-pny-config
2. 启动 pny-server中的业务微服务：pny-server-upms....
3. 启动 pny-client中的聚合接口：pny-client-admin...


