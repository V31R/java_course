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

                print(isPrint);

            }
            case TOGGLE -> {

                try {

                    Integer index = Integer.valueOf(command.getArguments());

                    index--;
                    if(tasks.get(index) == null||index>=tasks.size()){

                        System.err.println("Incorrect index for " + command.getType().name().toLowerCase(Locale.ROOT));
                        return;

                    }
                    toggle(index);

                }
                catch (NumberFormatException e) {

                    System.err.println("Incorrect argument for " + command.getType().name().toLowerCase(Locale.ROOT));

                }

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

}