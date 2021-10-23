package kalchenko.task;
import kalchenko.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TaskList {

    private List<Task> tasks;

    public TaskList() {

        this.tasks = new ArrayList<>();

    }

    public  void performCommand(Command command) throws NumberFormatException{

        switch(command.getType()){

            case ADD -> {

                add(command.getArguments());

            }
            case PRINT-> {

                boolean isPrint = false;
                if(command.getArguments()!=null && command.getArguments().equals("all")){

                    isPrint=true;

                }
                else if(command.getArguments()!=null){

                    throw new IllegalArgumentException("Incorrect argument for " + command.getType().name().toLowerCase(Locale.ROOT));

                }

                print(isPrint);

            }
            case TOGGLE -> {

                toggleAndDeleteSwitch(command);

            }
            case DELETE -> {

                toggleAndDeleteSwitch(command);

            }

        }

    }


    public void add(String description){

        tasks.add(new Task(description));
        System.out.println("Task " + description + " was added");

    }

    public void print(boolean isPrint){

        for(int i = 0; i < tasks.size(); i++){

            if(tasks.get(i).getState() || isPrint){

                print(i);
                System.out.println();
            }

        }

    }

    private void print(int index){

        System.out.printf("%d.[%c] %s", index + 1, tasks.get(index).getState()?'x':' ' ,tasks.get(index).getDescription());

    }

    public  void toggle(int index){

        tasks.get(index).toggleState();

        print(index);
        System.out.printf(" toggled to %b\n", tasks.get(index).getState());

    }
    public  void delete(int index){

        tasks.remove(index);

    }

    private void toggleAndDeleteSwitch(Command command) throws NumberFormatException{

        try {

            Integer index = Integer.valueOf(command.getArguments());

            index--;
            if(index>=tasks.size()||tasks.get(index) == null){

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
        catch (NumberFormatException e) {

            throw new NumberFormatException("Incorrect argument for " + command.getType().name().toLowerCase(Locale.ROOT));


        }

    }

}