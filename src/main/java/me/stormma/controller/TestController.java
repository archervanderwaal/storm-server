package me.stormma.controller;

import me.stormma.http.annotation.Api;
import me.stormma.http.annotation.Controller;
import me.stormma.http.annotation.JsonParam;
import me.stormma.http.annotation.RequestParam;
import me.stormma.http.enums.RequestMethod;
import me.stormma.http.response.Response;
import me.stormma.http.response.builder.ResponseBuilder;
import me.stormma.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stormma
 * @date 2017/8/14.
 */
@Controller("/api")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Api(url = "/host", method = RequestMethod.POST)
    public Response<String> test(@RequestParam(name = "name") String name, @JsonParam User user) {
        System.out.println(name);
        System.out.println(user);
        return ResponseBuilder.success(name);
    }
}
