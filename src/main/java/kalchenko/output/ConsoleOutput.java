package kalchenko.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConsoleOutput implements BaseOutput {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutput.class);

    private static ConsoleOutput instance;

    private ConsoleOutput()
    {}

    public static ConsoleOutput getInstance(){

        if(instance==null){

            instance= new ConsoleOutput();

        }

        return instance;

    }
    public void output(String message){

        System.out.println(message);
        logger.debug("Output: {}", message);

    }

}
