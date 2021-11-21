package kalchenko.commandStrategy;

import kalchenko.command.Command;
import kalchenko.task.Task;
import kalchenko.task.TaskList;

import java.util.LinkedHashMap;

public class ConcreteStrategyAdd implements CommandStrategy{

    @Override
    public void execute(TaskList taskList, Command command) {

        taskList.setCurrent_id(taskList.getCurrent_id() + 1);
        LinkedHashMap<Integer, Task> tasks=taskList.getTasks();
        tasks.put(taskList.getCurrent_id(),new Task(command.getArguments()));
        taskList.setTasks(tasks);

    }

}
