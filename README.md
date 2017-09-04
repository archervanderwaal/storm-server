#### storm-server

##### 什么是storm-server?为什么有storm-server
> storm-server是一个以jetty为内置servlet容器的一个java web框架，主要用于java提供api服务来使用，参数传递和请求的响应的数据类型都是JSON，
storm-server设计的初衷就是练习自己实现一个轻量级的框架(或许这压根算不上是一个框架)，或许后面可以用于自己项目中，快速开发出适用于自己系统的
后端服务。storm-server后期会继续维护，增加其他的功能，当然如果有人感兴趣，也可以加入项目中，贡献自己的一份力。

##### 怎么用
1. 引入依赖，当然后期会将项目发布到maven中心仓库。
2. 新建项目入口类
``` java
import me.stormma.annotation.ComponentScan;
import me.stormma.annotation.Application;

/**
 * @author stormma
 * @date 2017/8/14.
 */
@ComponentScan
@Application(StormApplicationTest.class)
public class StormApplicationTest {
    public static void main(String[] args) {
        StormApplication.run(args);
    }
}
```

3. 创建一个controller处理请求

```java
import me.stormma.http.annotation.Api;
import me.stormma.ioc.annotation.Controller;
import me.stormma.http.annotation.JsonParam;
import me.stormma.http.annotation.RequestParam;
import me.stormma.http.enums.RequestMethod;
import me.stormma.http.response.Response;
import me.stormma.http.response.builder.ResponseBuilder;
import me.stormma.controller.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stormma
 * @date 2017/8/14.
 */
@Controller("/api")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Api(url = "/test", method = RequestMethod.POST)
    public Response<String> test(@RequestParam(name = "name") String name, @JsonParam User user) {
        System.out.println(name);
        System.out.println(user);
        return ResponseBuilder.success(name);
    }
}
```

4. 添加storm.properties配置文件

```properties
#作用通模块名
storm.server_id=520757
#端口，默认8057
storm.port=8080
#模块名，主要是为了区分多模块下错误定位
storm.module_name=storm
#异常接收邮件地址,如果没有就证明没有启用邮件通知异常服务
storm.mail.email_to_address=1325338799@qq.com
....
```

5. 运行main方法，启动storm-server

#### 特性
1. IOC
2. 内置Jetty
##### 结束语
> storm-server开发已有一周时间，很多核心的功能还没有实现，后期会加入ioc等功能，storm-server第一版开发完成之后，会发布到maven仓库，欢迎
各位使用，以及参与storm-server的开发，期待你的参与与建议。
