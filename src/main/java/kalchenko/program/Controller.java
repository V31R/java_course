package kalchenko.program;

import kalchenko.command.*;
import kalchenko.input_class.TerminalReader;
import kalchenko.task.TaskList;

import java.io.IOException;

public class Controller {

    private static Controller instance;

    private TaskList taskList;
    private TerminalReader terminalReader;

    private Controller() {

        terminalReader=TerminalReader.getInstance();
        taskList= new TaskList();

    }

    public static  Controller getInstance(){

        if(instance==null){

            instance= new  Controller();

        }

        return instance;

    }

    public void execute(){

        boolean open = true;

        while(open){

            Command command;

            try{

                command=terminalReader.inputCommand();

            }
            catch (IllegalArgumentException | IOException illegalArgumentException){

                System.out.println(illegalArgumentException.getMessage());
                command= new Command(CommandType.QUIT);

            }

            if(command.getType()==CommandType.QUIT){

                open = false;

            }
            else{

                try {

                    taskList.performCommand(command);

                }
                catch (NumberFormatException numberFormatException){

                    System.out.println(numberFormatException.getMessage());
                    open = false;

                }

            }

        }

        System.out.println("Program was finished.");


    }

}

