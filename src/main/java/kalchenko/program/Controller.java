package kalchenko.program;

import kalchenko.command.*;
import kalchenko.input_class.TerminalReader;
import kalchenko.task.TaskList;

import java.io.IOException;

import kalchenko.output.ConsoleOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

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

            Command command=null;

            try{

                command=terminalReader.inputCommand();

            }
            catch (IllegalArgumentException|IOException exception){

                ConsoleOutput.getInstance().output(exception.getMessage());
                logger.error(exception.getMessage());

            }


            if(command!=null){

                if(command.getType()==CommandType.QUIT){

                    open = false;

                }

                try {

                    taskList.performCommand(command);

                }
                catch (IllegalArgumentException illegalArgumentException){

                   ConsoleOutput.getInstance().output(illegalArgumentException.getMessage());
                   logger.error(illegalArgumentException.getMessage());

                }


            }

        }

    }

}

