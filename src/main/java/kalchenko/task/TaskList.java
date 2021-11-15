package kalchenko.task;
import kalchenko.command.Command;
import kalchenko.command.CommandType;

import java.util.*;

public class TaskList {

    private int current_id;
    private LinkedHashMap<Integer, Task> tasks;

    public TaskList() {

        this.tasks = new LinkedHashMap<>();
        this.current_id = 1;
    }

    public  void performCommand(Command command) throws NumberFormatException{

        switch(command.getType()){

            case ADD -> {

                add(command.getArguments());

            }
            case PRINT-> {

                boolean isPrint = false;
                if(command.getArguments()!=null){

                    isPrint=true;

                }

                print(isPrint);

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

        System.out.printf("%d.[%c] %s \n", task.getKey(), task.getValue().getState()?'x':' ' ,task.getValue().getDescription());

    }

    public void toggle(int index){

        tasks.get(index).toggleState();

    }
    public void delete(int index){

        tasks.remove(index);

    }

    private void toggleAndDeleteSwitch(Command command) throws NumberFormatException{

        Integer index = Integer.valueOf(command.getArguments());

        if(!tasks.containsKey(index)){

            throw new IllegalArgumentException("Incorrect index for " + command.getType().name().toLowerCase(Locale.ROOT));

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

    public void edit(String arguments){

        String[] args = arguments.split(" ");

        Integer index = Integer.valueOf(args[0]);

        if(!tasks.containsKey(index)){

            throw new IllegalArgumentException("Incorrect index for " + CommandType.EDIT.name().toLowerCase(Locale.ROOT));

        }


        StringBuilder description= new StringBuilder();
        for(int i = 1; i < args.length; i++){

            description.append(args[i]).append(" ");

        }

        tasks.get(index).setDescription(description.toString().trim());

    }

}