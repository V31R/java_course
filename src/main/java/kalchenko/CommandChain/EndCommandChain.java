package kalchenko.CommandChain;

import kalchenko.command.Command;

public class EndCommandChain extends  BaseHandler{

    @Override
    public Command handle(Command command) {

        return command;

    }

}
