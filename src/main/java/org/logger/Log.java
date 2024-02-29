package org.logger;

import java.util.logging.Logger;

public class Log {
    private final Logger logger;
    public Log(String className) {
        this.logger = Logger.getLogger(className);
    }
    public void info (String message) {
        logger.info(message);
    }

    public void warning (String message) {
        logger.warning(message);
    }

    public void error (String message) {
        logger.severe(message);
    }
}
