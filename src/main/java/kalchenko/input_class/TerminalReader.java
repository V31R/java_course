package kalchenko.input_class;

import kalchenko.command.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public final class TerminalReader {


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
            inputCommand=string.split(" ");

        }
        String commandName = inputCommand[0];
        commandName=commandName.trim();
        commandName = commandName.toUpperCase(Locale.ROOT);

        CommandType commandType = CommandType.getType(commandName);
        if(commandType==null){

            throw new IllegalArgumentException("Incorrect command was entered!");

        }
        if(!commandType.commandArgumentVerification(inputCommand)){

            throw new IllegalArgumentException("Incorrect argument for " + commandType.name().toLowerCase(Locale.ROOT));

        }
        result = new Command(commandType);

        if(inputCommand.length>1) {

            StringBuilder arguments = new StringBuilder("");
            for(int i=1; i < inputCommand.length;i++){

                arguments.append(inputCommand[i]).append(" ");

            }

            result.setArguments(arguments.toString().trim());

        }

        return result;

    }

}
