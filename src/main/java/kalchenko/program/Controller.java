package kalchenko.program;

import kalchenko.command.*;
import kalchenko.exception.ExceptionWithLogger;
import kalchenko.input_class.TerminalReader;
import kalchenko.task.TaskList;

import java.io.IOException;

import kalchenko.logger.Logback;
import kalchenko.output.ConsoleOutput;

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

            Command command=null;

            try{

                command=terminalReader.inputCommand();

            }
            catch (ExceptionWithLogger exceptionWithLogger){

                ConsoleOutput.output(exceptionWithLogger.getMessage());
                Logback.error(exceptionWithLogger.getMessage(),exceptionWithLogger.getLogger());

            }
            catch (IOException ioException){

                ConsoleOutput.output(ioException.getMessage());
                Logback.error(ioException.getMessage());

            }

            if(command!=null){

                if(command.getType()==CommandType.QUIT){

                    open = false;

                }

                try {

                    taskList.performCommand(command);

                }
                catch (ExceptionWithLogger exceptionWithLogger){

                    ConsoleOutput.output(exceptionWithLogger.getMessage());
                    Logback.error(exceptionWithLogger.getMessage(),exceptionWithLogger.getLogger());

                }


            }

        }

    }

}

