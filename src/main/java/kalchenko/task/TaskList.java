package kalchenko.task;
import kalchenko.command.Command;
import kalchenko.command.CommandType;
import kalchenko.output.ConsoleOutput;
import kalchenko.exception.ExceptionMessages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TaskList {

    private static final Logger logger = LoggerFactory.getLogger(TaskList.class);

    private int current_id;
    private LinkedHashMap<Integer, Task> tasks;

    public TaskList() {

        this.tasks = new LinkedHashMap<>();
        this.current_id = 1;
    }

    public  void performCommand(Command command) throws IllegalArgumentException {

        switch(command.getType()){

            case ADD -> {

                add(command.getArguments());

            }
            case PRINT-> {

                print(command.getArguments() != null);

            }
            case SEARCH -> {

                search(command.getArguments());

            }
            case TOGGLE, DELETE -> {

                toggleAndDeleteSwitch(command);

            }
            case EDIT -> {

                edit(command.getArguments());

            }
        }

    }


    public void add(String description){

        tasks.put(current_id++,new Task(description));

    }

    public void print(boolean isPrint){

        tasks.entrySet().stream()
                .filter((t)->t.getValue().getState()||isPrint)
                .forEach(TaskList::print_task);

    }

    static private void print_task(Map.Entry<Integer,Task> task){

        StringBuilder stringBuilder=new StringBuilder(task.getKey().toString());
        stringBuilder.append(". [")
                .append(task.getValue().getState() ? "x" : " ")
                .append("] ")
                .append(task.getValue().getDescription())
                .append("\n");
        ConsoleOutput.getInstance().output(stringBuilder.toString());

    }

    public void toggle(int index){

        tasks.get(index).toggleState();

    }
    public void delete(int index){

        tasks.remove(index);

    }

    private void toggleAndDeleteSwitch(Command command) throws IllegalArgumentException {

        Integer index = Integer.valueOf(command.getArguments());

        if(!tasks.containsKey(index)){

            logger.error(ExceptionMessages.incorrectIndex(CommandType.EDIT));
            throw new IllegalArgumentException(ExceptionMessages.incorrectIndex(CommandType.EDIT));

        }

        switch(command.getType()){
            case TOGGLE -> {

                toggle(index);

            }
            case DELETE -> {

                delete(index);

            }
        }

    }

    public void search(String subString){

        tasks.entrySet().stream()
                .filter((t)->t.getValue().getDescription().lastIndexOf(subString)!=-1)
                .forEach(TaskList::print_task);

    }

    public void edit(String arguments) throws IllegalArgumentException {

        String[] args = arguments.split(" ");

        Integer index = Integer.valueOf(args[0]);

        if(!tasks.containsKey(index)){

            logger.error(ExceptionMessages.incorrectIndex(CommandType.EDIT));
            throw new IllegalArgumentException(ExceptionMessages.incorrectIndex(CommandType.EDIT));

        }


        StringBuilder description= new StringBuilder();
        for(int i = 1; i < args.length; i++){

            description.append(args[i]).append(" ");

        }

        tasks.get(index).setDescription(description.toString().trim());

    }

}