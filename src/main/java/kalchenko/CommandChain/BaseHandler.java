package kalchenko.CommandChain;

import kalchenko.command.Command;

public class BaseHandler implements  Handler{

    BaseHandler next=null;

    @Override
    public Command handle(Command command) {

        if(next!=null){

            return next.handle(command);

        }

        return command;

    }

    public void setNext(BaseHandler handler) {

        next=handler;

    }

}
