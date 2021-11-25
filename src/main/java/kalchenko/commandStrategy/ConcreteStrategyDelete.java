package kalchenko.commandStrategy;

import kalchenko.command.Command;
import kalchenko.exception.ExceptionMessages;
import kalchenko.task.TaskList;

public class ConcreteStrategyDelete implements CommandStrategy{

    @Override
    public void execute(TaskList taskList, Command command) {

        Integer index = Integer.valueOf(command.getArguments());

        if(!taskList.getTasks().containsKey(index)){

            throw new IllegalArgumentException(ExceptionMessages.incorrectIndex(command.getType()));

        }

        taskList.delete(index);

    }

}
