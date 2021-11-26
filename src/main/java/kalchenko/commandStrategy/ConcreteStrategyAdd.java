package kalchenko.commandStrategy;

import kalchenko.command.Command;
import kalchenko.task.TaskList;

public class ConcreteStrategyAdd implements CommandStrategy{

    @Override
    public void execute(TaskList taskList, Command command) {

        taskList.add(command.getArguments());

    }

}
