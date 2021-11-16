package kalchenko.exception;

import org.slf4j.Logger;

public class ExceptionWithLogger extends Exception {

    private Logger logger;

    public ExceptionWithLogger(String message, Logger logger) {

        super(message);
        this.logger = logger;

    }

    public Logger getLogger(){

        return  logger;

    }

}
