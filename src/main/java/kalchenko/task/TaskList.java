package kalchenko.task;
import kalchenko.command.Command;
import kalchenko.commandStrategy.*;

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
        this.current_id = 1;
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

    public void add(String description){

        tasks.put(current_id++,new Task(description));

    }

    public void toggle(int index){

        tasks.get(index).toggleState();

    }
    public void delete(int index){

        tasks.remove(index);

    }

    public void edit(Integer index, String newDescription){

        tasks.get(index).setDescription(newDescription);

    }

    public LinkedHashMap<Integer, Task> getTasks() {

        return tasks;

    }


}