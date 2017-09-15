package me.stormma.support.listener;

import me.stormma.annotation.Application;
import me.stormma.core.config.Environment;
import me.stormma.exception.StormServerException;
import me.stormma.fault.InitializationError;
import me.stormma.support.helper.ApplicationHelper;
import me.stormma.support.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stormma
 * @date 2017/09/12
 */
public class StormApplicationContextRunListener extends AbstractStormApplicationRunListener {

    private String basePackageName;

    private static final Logger logger = LoggerFactory.getLogger(StormApplicationContextRunListener.class);

    @Override
    public void starting() {
        if (StringUtils.isEmpty(basePackageName)) {
            throw new InitializationError("");
        }
        ApplicationHelper.initStormApplicationIoc(basePackageName);
        try {
            ApplicationHelper.initStormApplicationApiMap(basePackageName);
        } catch (StormServerException e) {
            logger.error(e.getMessage());
            System.exit(-1);
        }
        ApplicationHelper.initStormApplicationConverterMap(basePackageName);
    }

    @Override
    public void environmentPrepared(Environment environment) {
        this.basePackageName = environment.getStormApplicationComponentScanPackage();
    }
}
