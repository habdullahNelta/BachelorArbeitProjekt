package qa.util;

import java.util.logging.Level;

public class Logger {

    public static void logger(Level level, String message) {
        java.util.logging.Logger logger = java.util.logging.Logger.getAnonymousLogger();
        logger.log(level, message);
    }
}
