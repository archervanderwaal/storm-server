package me.stormma.controller;

import me.stormma.http.annotation.Api;
import me.stormma.http.annotation.Controller;
import me.stormma.http.annotation.JsonParam;
import me.stormma.http.annotation.RequestParam;
import me.stormma.http.enums.RequestMethod;
import me.stormma.http.response.Response;
import me.stormma.http.response.builder.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author stormma
 * @date 2017/8/14.
 */
@Controller("/api")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Api(url = "/host", method = RequestMethod.GET)
    public Response<String> test(@RequestParam(name = "name") String is, @RequestParam(name = "startDate")Date startDate) {
        System.out.println(is);
        logger.info("测试==>{}", "127.0.0.1");
        return ResponseBuilder.success("127.0.0.1");
    }
}