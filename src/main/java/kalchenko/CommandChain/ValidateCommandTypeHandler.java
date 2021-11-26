package kalchenko.CommandChain;

import kalchenko.command.Command;
import kalchenko.command.CommandType;
import kalchenko.exception.ExceptionMessages;

import java.util.Locale;

public class ValidateCommandTypeHandler extends BaseHandler{

    @Override
    public Command handle(Command command) {

        String commandName = command.getArguments(true)[0];
        commandName=commandName.trim();
        commandName = commandName.toUpperCase(Locale.ROOT);

        CommandType commandType = CommandType.getType(commandName);

       if(commandType == null){

            throw new IllegalArgumentException(ExceptionMessages.incorrectCommand());

        }

        Command validatedCommand=new Command(commandType,command.getArguments(true));
        if(next!=null){

            return next.handle(validatedCommand);

        }

        return command;

    }

}
