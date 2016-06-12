package io.github.sporklibrary.logging.implementations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.logging.Logger;

public class DefaultLogger implements Logger {

    private final String name;

    public static final class Level {
        public final static int TRACE = 0;
        public final static int DEBUG = 10;
        public final static int INFO = 20;
        public final static int WARN = 30;
        public final static int ERROR = 40;
    }

    DefaultLogger(String name) {
        this.name = name;
    }

    private void log(int logLevel, String format, @Nullable Object[] arguments, @Nullable Throwable caught) {

        if (logLevel < DefaultLoggerSettings.getLogLevel()) {
            return;
        }

        // Create a builder to create a buffer to support multi-threaded logging
        StringBuilder builder = new StringBuilder();

        // Prefix with date, log level and logger name
        String datePart = DefaultLoggerSettings.getDateFormat().format(new Date());
        if (datePart.length() > 0) {
            builder.append(datePart);
            builder.append(' ');
        }

        // Add level name if it is not empty
        String levelName = getLevelName(logLevel);
        if (!levelName.isEmpty()) {
            builder.append(levelName);
            builder.append(' ');
        }

        builder.append(name);
        builder.append(" - ");

        // Print actual log message (with formatting where needed)
        if (arguments == null) {
            builder.append(format);
        } else {
            String[] items = format.split("\\{\\}");

            if ((items.length - 1) != arguments.length && !format.endsWith("{}")) {
                throw new RuntimeException("argument count mismatch");
            }

            printFormatted(builder, items, arguments);
        }

        // Print stack trace to StringBuilder
        if (caught != null) {
            // gather stacktrace string
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            caught.printStackTrace(printStream);

            builder.append('\n');
            builder.append(outputStream.toString());
        }

        // Finalize with newline
        builder.append('\n');

        // Output the StringBuilder to the configured PrintStream
        PrintStream printStream = DefaultLoggerSettings.getPrintStream(logLevel);
        printStream.print(builder.toString());
    }

    /**
     * Print out the splitted format string and its arguments
     *
     * @param formatItems the splitted format string
     * @param arguments   the arguments
     */
    private static void printFormatted(StringBuilder builder, String[] formatItems, Object[] arguments) {
        for (int i = 0; i < formatItems.length; i++) {
            builder.append(formatItems[i]);
            if (i < arguments.length) {
                builder.append(toString(arguments[i]));
            }
        }

        if (arguments.length > formatItems.length) {
            for (int i = formatItems.length; i < arguments.length; i++) {
                builder.append(toString(arguments[i]));
            }
        }
    }

    private static String toString(Object object) {
        if (object instanceof Class) {
            return ((Class<?>)object).getName();
        } else {
            return object.toString();
        }
    }

    private static String getLevelName(int logLevel) {
        switch (logLevel) {
            case 0:
                return "TRACE";
            case 10:
                return "DEBUG";
            case 20:
                return "INFO";
            case 30:
                return "WARN";
            case 40:
                return "ERROR";
            default:
                return "";
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void trace(String format) {
        log(Level.TRACE, format, null, null);
    }

    @Override
    public void trace(String format, Throwable caught) {
        log(Level.TRACE, format, null, caught);
    }

    @Override
    public void trace(String format, Object... arguments) {
        log(Level.TRACE, format, arguments, null);
    }

    @Override
    public boolean isTraceEnabled() {
        return DefaultLoggerSettings.getLogLevel() <= Level.TRACE;
    }

    @Override
    public void debug(String format) {
        log(Level.DEBUG, format, null, null);
    }

    @Override
    public void debug(String format, Throwable caught) {
        log(Level.DEBUG, format, null, caught);
    }

    @Override
    public void debug(String format, Object... arguments) {
        log(Level.DEBUG, format, arguments, null);
    }

    @Override
    public boolean isDebugEnabled() {
        return DefaultLoggerSettings.getLogLevel() <= Level.DEBUG;
    }

    @Override
    public void info(String format) {
        log(Level.INFO, format, null, null);
    }

    @Override
    public void info(String format, Throwable caught) {
        log(Level.INFO, format, null, caught);
    }

    @Override
    public void info(String format, Object... arguments) {
        log(Level.INFO, format, arguments, null);
    }

    @Override
    public boolean isInfoEnabled() {
        return DefaultLoggerSettings.getLogLevel() <= Level.INFO;
    }

    @Override
    public void warn(String format) {
        log(Level.WARN, format, null, null);
    }

    @Override
    public void warn(String format, Throwable caught) {
        log(Level.WARN, format, null, caught);
    }

    @Override
    public void warn(String format, Object... arguments) {
        log(Level.WARN, format, arguments, null);
    }

    @Override
    public boolean isWarnEnabled() {
        return DefaultLoggerSettings.getLogLevel() <= Level.WARN;
    }

    @Override
    public void error(String format) {
        log(Level.ERROR, format, null, null);
    }

    @Override
    public void error(String format, Throwable caught) {
        log(Level.ERROR, format, null, caught);
    }

    @Override
    public void error(String format, Object... arguments) {
        log(Level.ERROR, format, arguments, null);
    }

    @Override
    public boolean isErrorEnabled() {
        return DefaultLoggerSettings.getLogLevel() <= Level.ERROR;
    }
}
