package kalchenko.input_class;
import kalchenko.exception.ExceptionWithLogger;
import kalchenko.command.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import kalchenko.exception.ExceptionMessage;

import kalchenko.logger.Logback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public final class TerminalReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalReader.class);

    private static final BufferedReader  inputStream = new BufferedReader(new InputStreamReader(System.in));

    private static TerminalReader instance;

    private TerminalReader()
    {}

    public static TerminalReader getInstance(){

        if(instance==null){

            instance= new TerminalReader();

        }

        return instance;

    }

    public Command inputCommand() throws ExceptionWithLogger, IOException {

        Command result = null;
        String[] inputCommand;
        {

            String string = inputStream.readLine();
            Logback.debug("Input: " + string, LOGGER);
            inputCommand=string.split(" ");

        }
        String commandName = inputCommand[0];
        commandName=commandName.trim();
        commandName = commandName.toUpperCase(Locale.ROOT);

        CommandType commandType = CommandType.getType(commandName);
        if(commandType==null){

            throw new ExceptionWithLogger(ExceptionMessage.incorrectCommand(), LOGGER);

        }
        if(!commandType.commandArgumentVerification(inputCommand)){

            throw new ExceptionWithLogger(ExceptionMessage.incorrectArgument(commandType), LOGGER);

        }
        result = new Command(commandType);

        if(inputCommand.length > 1) {

            StringBuilder arguments = new StringBuilder("");
            for(int i=1; i < inputCommand.length;i++){

                arguments.append(inputCommand[i]).append(" ");

            }

            result.setArguments(arguments.toString().trim());

        }

        return result;

    }

}
