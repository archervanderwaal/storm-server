package me.stormma.logback;

import ch.qos.logback.access.joran.JoranConfigurator;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.FileUtil;
import ch.qos.logback.core.util.StatusPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author stormma
 * @date 2017/09/11
 * logback
 */
public class Logback {
    public static void load(String externalConfigFileLocation, ClassLoader classLoader) throws IOException, JoranException {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        URL externalConfigUrl = classLoader.getResource(externalConfigFileLocation);
        System.out.println(externalConfigUrl);
        File externalConfigFile = new File(externalConfigUrl.getPath());
        if (!externalConfigFile.exists()) {
            throw new IOException("Logback External Config File Parameter does not reference a file that exists");
        } else {
            if (!externalConfigFile.isFile()) {
                throw new IOException("Logback External Config File Parameter exists, but does not reference a file");
            } else {
                if (!externalConfigFile.canRead()) {
                    throw new IOException("Logback External Config File exists and is a file, but cannot be read.");
                } else {
                    JoranConfigurator configurator = new JoranConfigurator();
                    configurator.setContext(lc);
                    lc.reset();
                    configurator.doConfigure(externalConfigFileLocation);
                    StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
                }
            }
        }
    }
}
