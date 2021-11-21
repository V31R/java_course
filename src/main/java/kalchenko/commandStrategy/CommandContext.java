package kalchenko.commandStrategy;

import kalchenko.command.Command;
import kalchenko.task.TaskList;

public class CommandContext {

    private CommandStrategy strategy= null;

    public void setStrategy(CommandStrategy commandStrategy){

        strategy=commandStrategy;

    }

    public void execute(TaskList taskList, Command command) throws IllegalArgumentException{

        strategy.execute(taskList,command);

    }

}
