package kalchenko.task;

import kalchenko.command.*;
import kalchenko.commandStrategy.*;

import org.springframework.stereotype.Component;

@Component
public class TaskListController {

    private TaskList taskList;

    private CommandContext commandContext;

    private TaskListController() {

        taskList= new TaskList();
        commandContext=new CommandContext();

    }

    public void performCommand (Command command) throws IllegalArgumentException{

        switch(command.getType()){

            case ADD -> commandContext.setStrategy(new ConcreteStrategyAdd());
            case PRINT->  commandContext.setStrategy(new ConcreteStrategyPrint());
            case SEARCH ->  commandContext.setStrategy(new ConcreteStrategySearch());
            case TOGGLE ->  commandContext.setStrategy(new ConcreteStrategyToggle());
            case DELETE ->  commandContext.setStrategy(new ConcreteStrategyDelete());
            case EDIT ->  commandContext.setStrategy(new ConcreteStrategyEdit());
            case QUIT -> { return; }

        }

        commandContext.execute(taskList, command);

    }

}

