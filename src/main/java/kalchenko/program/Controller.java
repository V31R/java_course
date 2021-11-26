package kalchenko.program;

import kalchenko.command.*;
import kalchenko.commandStrategy.*;
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

    private CommandContext commandContext;

    private Controller() {

        terminalReader=TerminalReader.getInstance();
        taskList= new TaskList();
        commandContext=new CommandContext();

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
            catch (IllegalArgumentException|IOException exception){

                ConsoleOutput.getInstance().output(exception.getMessage());
                logger.error(exception.getMessage());
                continue;

            }

            if(command.getType()==CommandType.QUIT){

                open = false;

            }

            try {

                performCommand(taskList, command);

            }
            catch (IllegalArgumentException illegalArgumentException){

                ConsoleOutput.getInstance().output(illegalArgumentException.getMessage());
                logger.error(illegalArgumentException.getMessage());

            }

        }

    }

    private void performCommand(TaskList taskList, Command command) throws IllegalArgumentException{

        switch(command.getType()){

            case ADD -> commandContext.setStrategy(new ConcreteStrategyAdd());
            case PRINT->  commandContext.setStrategy(new ConcreteStrategyPrint());
            case SEARCH ->  commandContext.setStrategy(new ConcreteStrategySearch());
            case TOGGLE ->  commandContext.setStrategy(new ConcreteStrategyToggle());
            case DELETE ->  commandContext.setStrategy(new ConcreteStrategyDelete());
            case EDIT ->  commandContext.setStrategy(new ConcreteStrategyEdit());
            case QUIT -> { return; }

        }

        commandContext.execute(taskList, command);

    }

}

