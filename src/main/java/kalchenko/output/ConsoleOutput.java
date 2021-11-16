package kalchenko.output;
import kalchenko.input_class.TerminalReader;
import kalchenko.logger.Logback;
import kalchenko.output.BaseOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConsoleOutput implements BaseOutput {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalReader.class);

    public static void output(String message){

        System.out.println(message);
        Logback.debug("Input: " + message, LOGGER);

    }

}
