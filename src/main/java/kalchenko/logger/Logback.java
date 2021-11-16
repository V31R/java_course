package kalchenko.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logback {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logback.class);
    Logback(){


    }
    public static void debug(String message) {

        if(LOGGER.isDebugEnabled()){

            LOGGER.debug(message);

        }

    }

    public static void info(String message) {

       if(LOGGER.isInfoEnabled()){

            LOGGER.info(message);

       }

    }
    public static void error(String message) {

        if(LOGGER.isErrorEnabled()){

            LOGGER.error(message);

        }

    }

    public static void debug(String message, Logger logger) {

        if(logger.isDebugEnabled()){

            logger.debug(message);

        }

    }

    public static void info(String message,Logger logger) {

        if(logger.isInfoEnabled()){

            logger.info(message);

        }

    }
    public static void error(String message,Logger logger) {

        if(logger.isErrorEnabled()){

            logger.error(message);

        }

    }

}