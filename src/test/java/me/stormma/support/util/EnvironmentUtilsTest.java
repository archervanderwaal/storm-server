package me.stormma.support.util;

import me.stormma.ansi.AnsiOutput;
import me.stormma.support.utils.EnvironmentUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class EnvironmentUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentUtilsTest.class);

    @Before
    public void setAnsi() {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
    }

    @Test
    public void test() {
        logger.info("os.name=>{}", EnvironmentUtils.getOsName());
    }
}
