package kalchenko.program;

import kalchenko.command.*;
import kalchenko.commandStrategy.*;
import kalchenko.input_class.TerminalReader;
import kalchenko.task.TaskList;

public class TaskListController {

    private static TaskListController instance;

    private TaskList taskList;
    private TerminalReader terminalReader;

    private CommandContext commandContext;

    private TaskListController() {

        terminalReader=TerminalReader.getInstance();
        taskList= new TaskList();
        commandContext=new CommandContext();

    }

    public static TaskListController getInstance(){

        if(instance==null){

            instance= new TaskListController();

        }

        return instance;

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

