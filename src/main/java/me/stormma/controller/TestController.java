package me.stormma.controller;

import me.stormma.controller.service.ITestService;
import me.stormma.core.http.annotation.Api;
import me.stormma.core.http.model.HttpContext;
import me.stormma.ioc.annotation.AutoWired;
import me.stormma.ioc.annotation.Controller;
import me.stormma.core.http.annotation.JsonParam;
import me.stormma.core.http.annotation.RequestParam;
import me.stormma.core.http.enums.RequestMethod;
import me.stormma.core.http.response.Response;
import me.stormma.core.http.response.builder.ResponseBuilder;
import me.stormma.controller.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author stormma
 * @date 2017/8/14.
 */
@Controller("/api")
public class TestController {

    @AutoWired
    private ITestService testService;

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * 测试无参数情况
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
     * @param name
     * @param user
     * @return
     */
    @Api(url = "/host", method = RequestMethod.POST)
    public Response<String> test(@RequestParam(name = "name") String name, @JsonParam User user) {
        System.out.println(name);
        testService.test();
        System.out.println(user);
        return ResponseBuilder.success(name);
    }


}
