package kalchenko.task;
import kalchenko.command.Command;
import kalchenko.command.CommandType;

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

        tasks.add(new Task(description));

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

    public void toggle(int index){

        tasks.get(index).toggleState();

    }
    public void delete(int index){

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

    public void search(String subString){

        for(int i = 0; i < tasks.size(); i++){

            if(tasks.get(i).getDescription().lastIndexOf(subString)!=-1){

                print(i);
                System.out.println();

            }

        }

    }

    public void edit(String arguments){

        String[] args = arguments.split(" ");

        if(args.length<2){

            throw new IllegalArgumentException("Incorrect argument for " + CommandType.EDIT.name().toLowerCase(Locale.ROOT));

        }
        try{

            Integer index = Integer.valueOf(args[0]);

            index--;

            if(index>=tasks.size()||tasks.get(index) == null){

                throw new IllegalArgumentException("Incorrect index for " + CommandType.EDIT.name().toLowerCase(Locale.ROOT));

            }


            String description =" ";
            for(int i = 1; i < args.length;i++){

                description=description.concat(args[i].concat(" "));

            }

            tasks.get(index).setDescription(description);

        }
        catch (NumberFormatException e) {

            throw new NumberFormatException("Incorrect argument for " + CommandType.EDIT.name().toLowerCase(Locale.ROOT));

        }

    }

}