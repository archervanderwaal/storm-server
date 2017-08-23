package me.stormma.controller;

import me.stormma.controller.service.ITestService;
import me.stormma.http.annotation.Api;
import me.stormma.ioc.annotation.AutoWired;
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
}
