package kalchenko.input_class;
import kalchenko.command.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
//import java.util.Scanner;

public final class TerminalReader {

    private static final BufferedReader  inputStream = new BufferedReader(new InputStreamReader(System.in));

    //private static final Scanner inputStream = new Scanner(System.in);

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
        result = new Command(commandType);

        if(inputCommand.length>1) {

            String arguments = new String("");
            for(int i=1; i < inputCommand.length;i++){

                arguments=arguments.concat(inputCommand[i].concat(" "));

            }

            arguments = arguments.trim();
            result.setArguments(arguments);

        }

        return result;

    }

}
