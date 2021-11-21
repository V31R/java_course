package kalchenko.commandStrategy;

import kalchenko.command.Command;
import kalchenko.exception.ExceptionMessages;
import kalchenko.task.Task;
import kalchenko.task.TaskList;

import java.util.LinkedHashMap;

public class ConcreteStrategyToggle implements  CommandStrategy{

    @Override
    public void execute(TaskList taskList, Command command) {

        Integer index = Integer.valueOf(command.getArguments());
        LinkedHashMap<Integer, Task> tasks = taskList.getTasks();

        if(!tasks.containsKey(index)){

            throw new IllegalArgumentException(ExceptionMessages.incorrectIndex(command.getType()));

        }

        tasks.get(index).toggleState();
        taskList.setTasks(tasks);

    }

}
