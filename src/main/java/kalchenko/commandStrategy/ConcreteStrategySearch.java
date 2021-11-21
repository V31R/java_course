package kalchenko.commandStrategy;

import kalchenko.command.Command;
import kalchenko.task.TaskList;

public class ConcreteStrategySearch implements CommandStrategy{

    @Override
    public void execute(TaskList taskList, Command command) {

        taskList.getTasks().entrySet().stream()
                .filter((t)->t.getValue().getDescription().lastIndexOf(command.getArguments()) != -1)
                .forEach(TaskList::print_task);

    }

}
