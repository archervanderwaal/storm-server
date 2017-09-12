package me.stormma.controller;

import me.stormma.controller.service.ITestService;
import me.stormma.core.http.annotation.Api;
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
    @Api(url = "/host", method = RequestMethod.POST)
    public Response<String> test(@RequestParam(name = "name") String name, @JsonParam User user) {
        System.out.println(name);
        testService.test();
        System.out.println(user);
        return ResponseBuilder.success(name);
    }

    @Api(url = "/hello", method = RequestMethod.GET)
    public Response<String> hello() {
        return ResponseBuilder.success("hello storm-server");
    }

    @Api(url = "/date", method = RequestMethod.GET)
    public Response<Date> getCurrentDate() {
        return ResponseBuilder.success(new Date());
    }
}
