package kalchenko.input_class;
import kalchenko.command.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import kalchenko.exception.ExceptionMessages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class TerminalReader {

    private static final Logger logger = LoggerFactory.getLogger(TerminalReader.class);

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

    public Command inputCommand() throws IllegalArgumentException, IOException {

        Command result = null;
        String[] inputCommand;
        {

            String string = inputStream.readLine();
            logger.debug("Input: {}" ,string);
            inputCommand=string.split(" ");

        }
        String commandName = inputCommand[0];
        commandName=commandName.trim();
        commandName = commandName.toUpperCase(Locale.ROOT);

        CommandType commandType;
        try{
            commandType = CommandType.getType(commandName);
        }
        catch(IllegalArgumentException | NullPointerException exception ){

            logger.error("{} With exception '{}'",ExceptionMessages.incorrectCommand(), exception.getMessage());
            throw new IllegalArgumentException(ExceptionMessages.incorrectCommand());

        }
        if(!commandType.commandArgumentVerification(inputCommand)){

            logger.error(ExceptionMessages.incorrectArgument(commandType));
            throw new IllegalArgumentException(ExceptionMessages.incorrectArgument(commandType));

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
