#### storm-server

```java
/**
 * @author stormma
 * @date 2017/09/14
 */
```

##### storm-server介绍
> storm-server, 以jetty为servlet容器的一个java web框架, 主要用于为前端提供api服务, 具有快速开发的优势。
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
import me.stormma.core.http.annotation.Api;
import me.stormma.ioc.annotation.Controller;
import me.stormma.core.http.annotation.JsonParam;
import me.stormma.core.http.annotation.RequestParam;
import me.stormma.core.http.enums.RequestMethod;
import me.stormma.core.http.response.Response;
import me.stormma.core.http.response.builder.ResponseBuilder;
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
storm.server.port=9090
storm.server.module=storm_server_test
storm.mail.email_to_address=1325338799@qq.com
storm.ansi.output.enabled=true
```
5. 运行main方法，启动storm-server

#### 特性
1. IOC
2. 内置Jetty
##### 结束语
> storm-server开发已有一周时间，很多核心的功能还没有实现，后期会加入ioc等功能，storm-server第一版开发完成之后，会发布到maven仓库，欢迎
各位使用，以及参与storm-server的开发，期待你的参与与建议。



<!-- Javadoc -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-javadoc-plugin</artifactId>
                <version>2.9.1</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                        <configuration>
                            <additionalparam>-Xdoclint:none</additionalparam>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-release-plugin</artifactId>
                <version>2.2.2</version>
                <configuration>
                    <arguments>-Dgpg.passphrase=${gpg.passphrase}</arguments>
                </configuration>
            </plugin>

            <!-- GPG -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-gpg-plugin</artifactId>
                <version>1.5</version>
                <executions>
                    <execution>
                        <phase>verify</phase>
                        <goals>
                            <goal>sign</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
