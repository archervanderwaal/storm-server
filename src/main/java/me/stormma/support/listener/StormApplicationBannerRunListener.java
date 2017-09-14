package me.stormma.support.listener;

import me.stormma.StormApplication;
import me.stormma.ansi.AnsiOutput;
import me.stormma.core.config.Environment;
import me.stormma.core.config.StormApplicationConfig;
import me.stormma.support.banner.Banner;
import me.stormma.support.banner.StormApplicationBanner;
import me.stormma.support.utils.EnvironmentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

/**
 * @author stormma
 * @date 2017/09/12
 */
public class StormApplicationBannerRunListener extends AbstractStormApplicationRunListener {

    private Banner banner;

    private Class stormApplicationSourceClass;

    private Logger logger;

    @Override
    public void starting() {
        Class clazz = stormApplicationSourceClass;
        if (this.banner != null) {
            banner.printBanner(StormApplicationBanner.class, new PrintStream(System.out));
        }
        if (logger == null) {
            logger = LoggerFactory.getLogger(StormApplication.class);
        }
        logger.info(String.format("Starting %s on %s (%s) by %s", clazz.getName().substring(clazz.getName()
                        .lastIndexOf(".") + 1, clazz.getName().length()), EnvironmentUtils.getOsName(), EnvironmentUtils.getProjectOutputDir(),
                EnvironmentUtils.getAuthor()));
    }

    @Override
    public void environmentPrepared(Environment environment) {
        if (StormApplicationConfig.ANSI_OUTPUT_ENABLED) {
            AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        } else {
            AnsiOutput.setEnabled(AnsiOutput.Enabled.NEVER);
        }
        this.banner = new StormApplicationBanner();
        this.stormApplicationSourceClass = environment.getStormApplicationSourceClass();
        this.logger = environment.getLogger();
    }
}
