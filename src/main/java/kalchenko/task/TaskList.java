package kalchenko.task;
import kalchenko.command.Command;

import java.util.Locale;

public class TaskList {

    private Task[] tasks;

    public TaskList() {

        this.tasks = new Task[1];

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
                    if(tasks[index] == null||index>=tasks.length){

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

        tasks = new Task[1];
        tasks[0]=new Task(description);
        tasks[0].setDescription(description);
        System.out.println("Task " + description + " was added");

    }

    public void print(boolean isPrint){

        for(int i = 0; i < tasks.length; i++){

            if(tasks[i].getState() || isPrint){

                print(i);
                System.out.println();
            }

        }

    }

    private void print(int index){

        System.out.printf("%d.[%c] %s", index + 1, tasks[index].getState()?'x':' ' ,tasks[index].getDescription());

    }

    public  void toggle(int index){

        tasks[index].toggleState();

        print(index);
        System.out.printf(" toggled to %b\n", tasks[index].getState());

    }

}