#### storm-server

```java
/**
 * @author stormma
 * @date 2017/09/14
 */
```

##### storm-server介绍
> storm-server, 以jetty为servlet容器的一个java web框架, 主要用于为前端提供api服务, 具有快速开发的优势。storm-server之后会提供一些操作mysql, redis的工具, storm-server旨在快速开发一些小型的web应用, 以及用于日常学习。storm-server github地址: https://github.com/stormmaybin/storm-server.git, 欢迎各位star和参与开发，storm-server期待你的参与与建议。

##### 小试牛刀
######  引入storm-server依赖(最新版本1.0，后期会跟进升级和维护)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.github.stormmaybin</groupId>
    <artifactId>storm-server-test</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <storm-server.version>1.0</storm-server.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>me.stormma</groupId>
            <artifactId>storm-server</artifactId>
            <version>${storm-server.version}</version>
        </dependency>
    </dependencies>
</project>

```

###### 添加storm.properties配置文件
```properties
storm.server.port=8057 # 端口默认为8057
storm.server.module=storm_server_test #模块名
storm.ansi.output.enabled=true # 不同级别日志显示颜色不同
```
> storm-server默认去classpath下读取storm.properties配置文件, 当然, 你也可以指定配置文件的路径和名字, 如果你选择这么做了，那么你要在运行启动类时候传入配置文件的完整路径, 例如: 假如我的配置文件名字叫application.properties, 放在resources/config/,那么你需要在运行启动类(下面会说到)的时候传入参数'resources/config/application.properties'。 

###### 启动storm-server
``` java
package com.github.stormmaybin.controller;

import me.stormma.StormApplication;
import me.stormma.annotation.Application;
import me.stormma.annotation.ComponentScan;

/**
 * @description 启动类
 * @author stormma 
 * @date 2017/09/15
 */
@ComponentScan
@Application(StormServerTestApplication.class)
public class StormServerTestApplication {
    public static void main(String[] args) {
        StormApplication.run(args);
    }
}
```

###### 新建service
```
package com.github.stormmaybin.controller.service.impl;

import com.github.stormmaybin.controller.model.User;
import com.github.stormmaybin.controller.service.ITestService;
import me.stormma.ioc.annotation.Service;

/**
 * @description
 * @author stormma
 * @date 2017/09/15
 */
@Service
public class TestService implements ITestService {

    @Override
    public User getUserById(int uid) {
        //模拟dao层操作
        User user = new User();
        user.setUsername("stormma");
        user.setPassword("stormma");
        return user;
    }
}
```
> @Service注解声明此类是一个service, 这与spring mvc/boot保持一致

###### 新建controller
```
package com.github.stormmaybin.controller.controller;

import com.github.stormmaybin.controller.model.User;
import com.github.stormmaybin.controller.service.ITestService;
import me.stormma.core.http.annotation.Api;
import me.stormma.core.http.annotation.JsonParam;
import me.stormma.core.http.annotation.RequestParam;
import me.stormma.core.http.enums.RequestMethod;
import me.stormma.core.http.model.HttpContext;
import me.stormma.core.http.response.Response;
import me.stormma.core.http.response.builder.ResponseBuilder;
import me.stormma.ioc.annotation.AutoWired;
import me.stormma.ioc.annotation.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author stormma
 * @date 2017/09/15
 */
@Controller("/api")
public class TestController {

    @AutoWired
    private ITestService testService;

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * 测试无参数情况的数据响应
     * @return
     */
    @Api(url = "/hello", method = RequestMethod.GET)
    public Response<String> hello() {
        return ResponseBuilder.success("hello storm-server");
    }

    /**
     * 测试绑定HttpContext
     * @param context
     * @return
     */
    @Api(url = "/date", method = RequestMethod.GET)
    public Response<Date> getCurrentDate(HttpContext context) {
        logger.info("访问路径:{}", context.requestPath);
        return ResponseBuilder.success(new Date());
    }

    @Api(url = "/array/int", method = RequestMethod.GET)
    public Response<String> testIntArray(@RequestParam(name = "id") int[] ids) {
        for (int id: ids) {
            logger.info("{}", id);
        }
        return ResponseBuilder.success();
    }

    @Api(url = "/array/Integer", method = RequestMethod.GET)
    public Response<String> testIntegerArray(@RequestParam(name = "id") Integer[] ids) {
        for (Integer id: ids) {
            logger.info("{}", id);
        }
        return ResponseBuilder.success();
    }

    @Api(url = "array/float", method = RequestMethod.GET)
    public Response<String> testFloatArray(@RequestParam(name = "id") float[] ids) {
        for (float id: ids) {
            logger.info("{}", id);
        }
        return ResponseBuilder.success();
    }

    @Api(url = "array/Float", method = RequestMethod.GET)
    public Response<String> testFloatArray(@RequestParam(name = "id") Float[] ids) {
        for (Float id: ids) {
            logger.info("{}", id);
        }
        return ResponseBuilder.success();
    }

    @Api(url = "array/Double", method = RequestMethod.GET)
    public Response<String> testDoubleArray(@RequestParam(name = "id") Double[] ids) {
        for (Double id: ids) {
            logger.info("{}", id);
        }
        return ResponseBuilder.success();
    }

    @Api(url = "array/double", method = RequestMethod.GET)
    public Response<String> testDoubleArray(@RequestParam(name = "id") double[] ids) {
        for (double id: ids) {
            logger.info("{}", id);
        }
        return ResponseBuilder.success();
    }

    @Api(url = "array/boolean", method = RequestMethod.GET)
    public Response<String> testBooleanArray(@RequestParam(name = "id") boolean[] ids) {
        for (boolean id: ids) {
            logger.info("{}", id);
        }
        return ResponseBuilder.success();
    }

    @Api(url = "array/Boolean", method = RequestMethod.GET)
    public Response<String> testBooleanArray(@RequestParam(name = "id") Boolean[] ids) {
        for (Boolean id: ids) {
            logger.info("{}", id);
        }
        return ResponseBuilder.success();
    }

    /**
     * 测试query string参数类型，和json参数类型结合的绑定
     * @param id
     * @param user
     * @return
     */
    @Api(url = "/get/user", method = RequestMethod.POST)
    public Response<User> test(@RequestParam(name = "id") int id, @JsonParam User user) {
        System.out.println(user);
        User result = testService.getUserById(id);
        logger.info("json 参数: {}", user);
        logger.info("{}", result);
        return ResponseBuilder.success(result);
    }
}
```
> storm-server建议响应数据统一化，controller method返回值都是Response<T> 类型，Response是storm-server提供的一个响应数据封装类。

```java
public class Response<T> {
    /**
     * error code :错误是1、成功是0
     */
    private Integer code;

    /**
     * 本次请求的status
     */
    private Integer status;

    /**
     * 要返回的数据
     */
    private T data;

    /**
     * 本次请求的说明信息
     */
    private String msg;
```
> controller的用法和spring boot/mvc差别不大，storm-server提供了参数自动绑定，内部提供了String2Boolean, 默认的String2Date，还有String2Number三种转换器, 当然，如果这三种转换器不够满足你项目的需求，你也可以自定义一个converter, 实现Converter<String, T>接口即可，具体的步骤为:

```
public class DefaultStringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) throws StormServerException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(source);
        } catch (ParseException e) {
            throw new StormServerException("string convert to date failed, because param is not valid.");
        }
        return date;
    }
}
```
> 同样，storm-server提供了json参数自动绑定到对象上，@JsonParam注解可以帮你完成这个冗余的操作。

###### 启动storm-server
```java

```