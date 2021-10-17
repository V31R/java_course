package kalchenko.input_class;
import kalchenko.command.*;

import java.util.Locale;
import java.util.Scanner;


public final class TerminalReader {

    private static final Scanner inputStream = new Scanner(System.in);

    private static TerminalReader instance;

    private TerminalReader()
    {}

    public static TerminalReader getInstance(){

        if(instance==null){

            instance= new TerminalReader();

        }

        return instance;

    }

    public Command inputCommand() throws IllegalArgumentException{

        Command result = null;

        String commandName = inputStream.next();
        commandName = commandName.toUpperCase(Locale.ROOT);
        CommandType commandType = CommandType.getType(commandName);
        if(commandType==null){

            throw new IllegalArgumentException("Incorrect command was entered!");

        }

        String arguments = inputStream.nextLine();
        String[] args=new String[1];
        args[0]=arguments.trim();

        result=new Command(commandType, args);
        return result;

    }

}
