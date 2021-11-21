package kalchenko.commandStrategy;

import kalchenko.command.Command;
import kalchenko.task.TaskList;

public class ConcreteStrategyPrint implements CommandStrategy {

    @Override
    public void execute(TaskList taskList, Command command) {

        taskList.getTasks().entrySet().stream()
                .filter((t)->t.getValue().getState() || command.getArguments() != null)
                .forEach(TaskList::print_task);

    }

}
