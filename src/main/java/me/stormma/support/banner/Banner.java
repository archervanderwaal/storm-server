package me.stormma.support.banner;

import java.io.PrintStream;

/**
 * @author stormma
 * @date 2017/09/11
 */
public interface Banner {
    /**
     * Print the banner to the specified print stream.
     * @param sourceClass
     * @param out
     */
    void printBanner(Class<?> sourceClass, PrintStream out);

    /**
     * An enumeration of possible values for configuring the Banner.
     */
    enum Mode {

        /**
         * Disable printing of the banner.
         */
        OFF,

        /**
         * Print the banner to System.out.
         */
        CONSOLE,

        /**
         * Print the banner to the log file.
         */
        LOG

    }
}
