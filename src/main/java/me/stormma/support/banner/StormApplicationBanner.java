package me.stormma.support.banner;

import me.stormma.ansi.AnsiColor;
import me.stormma.ansi.AnsiOutput;
import me.stormma.ansi.AnsiStyle;

import java.io.PrintStream;

public class StormApplicationBanner implements Banner {

    private static final String[] BANNER = {"",
            "          __\n" +
                    "  _______/  |_  ___________  _____  \\ \\ \\\n" +
                    " /  ___/\\   __\\/  _ \\_  __ \\/     \\  \\ \\ \\\n" +
                    " \\___ \\  |  | (  <_> )  | \\/  Y Y  \\  ) ) )\n" +
                    "/____  > |__|  \\____/|__|  |__|_|  / / / /\n" +
                    "     \\/                          \\/ / / /\n" +
                    "=========|_|=================|___/=/_/_/"
    };

    private static final String STORM_SERVER = " :: Storm Server :: ";

    private static final String STORM_SERVER_VERSION = "1.0.0";

    private static final int STRAP_LINE_SIZE = 42;

    @Override
    public void printBanner(Class<?> sourceClass, PrintStream printStream) {
        for (String line : BANNER) {
            printStream.println(line);
        }
        String version = " (v" + STORM_SERVER_VERSION + ")";
        String padding = "";
        while (padding.length() < STRAP_LINE_SIZE
                - (version.length() + STORM_SERVER.length())) {
            padding += " ";
        }
        printStream.println(AnsiOutput.toString(AnsiColor.GREEN, STORM_SERVER,
                AnsiColor.DEFAULT, padding, AnsiStyle.FAINT, version));
        printStream.println();
    }
}
