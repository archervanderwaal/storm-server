package me.stormma.controller.service.impl;

import me.stormma.controller.service.ITestService;
import me.stormma.ioc.annotation.Service;

/**
 * @description
 * @author stormma
 * @date 2017/8/23
 */
@Service
public class TestService implements ITestService {

    @Override
    public void test() {
        System.out.println("ssssss");
    }
}
