package kalchenko.CommandChain;

import kalchenko.command.Command;
import kalchenko.exception.ExceptionMessages;

public class ValidateCommandArgsHandler extends BaseHandler{

    @Override
    public Command handle(Command command) {

        if(!command.getType().commandArgumentVerification( command.getArguments(true))){

            throw new IllegalArgumentException(ExceptionMessages.incorrectArgument(command.getType()));

        }

        Command validatedCommand = new Command(command.getType());

        if(command.getArguments(true).length > 1) {

            StringBuilder arguments = new StringBuilder("");
            for(int i=1; i < command.getArguments(true).length;i++){

                arguments.append(command.getArguments(true)[i]).append(" ");

            }

            validatedCommand.setArguments(arguments.toString().trim());

        }else{

            command.setArguments(null, true);

        }

        if(next!=null){

             return next.handle(validatedCommand);

        }

        return command;

    }

}
