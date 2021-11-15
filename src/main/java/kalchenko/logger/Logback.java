package kalchenko.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logback {
    private static final Logger LOGGER = LoggerFactory.getLogger(Logback.class);

    public static void info(String message) {

       if(LOGGER.isInfoEnabled()){

            LOGGER.info(message);

       }

    }

}