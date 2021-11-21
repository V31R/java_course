package kalchenko.commandStrategy;

import kalchenko.command.Command;
import kalchenko.exception.ExceptionMessages;
import kalchenko.task.Task;
import kalchenko.task.TaskList;

import java.util.LinkedHashMap;

public class ConcreteStrategyEdit implements  CommandStrategy{

    @Override
    public void execute(TaskList taskList, Command command) {

        String[] args = command.getArguments().split(" ");

        Integer index = Integer.valueOf(args[0]);

        LinkedHashMap<Integer, Task> tasks = taskList.getTasks();

        if(!tasks.containsKey(index)){

            throw new IllegalArgumentException(ExceptionMessages.incorrectIndex(command.getType()));

        }


        StringBuilder description= new StringBuilder();
        for(int i = 1; i < args.length; i++){

            description.append(args[i]).append(" ");

        }

        tasks.get(index).setDescription(description.toString().trim());
        taskList.setTasks(tasks);

    }

}
