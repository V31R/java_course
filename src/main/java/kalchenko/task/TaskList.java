package kalchenko.task;
import kalchenko.command.Command;
import kalchenko.commandStrategy.*;
import kalchenko.output.ConsoleOutput;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TaskList {

    private static final Logger logger = LoggerFactory.getLogger(TaskList.class);

    private int current_id;

    private LinkedHashMap<Integer, Task> tasks;

    private CommandContext commandContext;

    public TaskList() {

        this.tasks = new LinkedHashMap<>();
        this.current_id = 0;
        commandContext=new CommandContext();

    }

    public  void performCommand(Command command) throws IllegalArgumentException {
        switch(command.getType()){

            case ADD -> commandContext.setStrategy(new ConcreteStrategyAdd());
            case PRINT->  commandContext.setStrategy(new ConcreteStrategyPrint());
            case SEARCH ->  commandContext.setStrategy(new ConcreteStrategySearch());
            case TOGGLE ->  commandContext.setStrategy(new ConcreteStrategyToggle());
            case DELETE ->  commandContext.setStrategy(new ConcreteStrategyDelete());
            case EDIT ->  commandContext.setStrategy(new ConcreteStrategyEdit());
            case QUIT -> { return; }

        }
        try {

            commandContext.execute(this, command);

        }
        catch (IllegalArgumentException illegalArgumentException){

            logger.error(illegalArgumentException.getMessage());
            throw new IllegalArgumentException(illegalArgumentException.getMessage());

        }

    }

    static public void print_task(Map.Entry<Integer,Task> task){

        StringBuilder stringBuilder=new StringBuilder(task.getKey().toString());
        stringBuilder.append(". [")
                .append(task.getValue().getState() ? "x" : " ")
                .append("] ")
                .append(task.getValue().getDescription())
                .append("\n");
        ConsoleOutput.getInstance().output(stringBuilder.toString());

    }

    public int getCurrent_id() {

        return current_id;

    }

    public void setCurrent_id(int current_id) {

        this.current_id = current_id;

    }

    public LinkedHashMap<Integer, Task> getTasks() {

        return tasks;

    }

    public void setTasks(LinkedHashMap<Integer, Task> tasks) {

        this.tasks = tasks;

    }

}