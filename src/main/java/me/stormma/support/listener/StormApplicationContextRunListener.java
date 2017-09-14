package me.stormma.support.listener;

import me.stormma.annotation.Application;
import me.stormma.core.config.Environment;
import me.stormma.fault.InitializationError;
import me.stormma.support.helper.ApplicationHelper;
import me.stormma.support.utils.StringUtils;

/**
 * @author stormma
 * @date 2017/09/12
 */
public class StormApplicationContextRunListener extends AbstractStormApplicationRunListener {

    private String basePackageName;

    @Override
    public void starting() {
        if (StringUtils.isEmpty(basePackageName)) {
            throw new InitializationError("");
        }
        ApplicationHelper.initStormApplicationIoc(basePackageName);
        ApplicationHelper.initStormApplicationApiMap(basePackageName);
        ApplicationHelper.initStormApplicationConverterMap(basePackageName);
    }

    @Override
    public void environmentPrepared(Environment environment) {
        this.basePackageName = environment.getStormApplicationComponentScanPackage();
    }
}
