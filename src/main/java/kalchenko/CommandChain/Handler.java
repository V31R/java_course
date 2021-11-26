package kalchenko.CommandChain;

import kalchenko.command.Command;

public interface Handler {

   Command handle(Command command);

}
