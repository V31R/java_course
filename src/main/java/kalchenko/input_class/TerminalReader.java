package kalchenko.input_class;
import kalchenko.CommandChain.BaseHandler;
import kalchenko.CommandChain.EndCommandChain;
import kalchenko.CommandChain.ValidateCommandArgsHandler;
import kalchenko.CommandChain.ValidateCommandTypeHandler;
import kalchenko.command.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import kalchenko.output.ConsoleOutput;
import kalchenko.task.TaskListController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public final class TerminalReader {

    private static final Logger logger = LoggerFactory.getLogger(TerminalReader.class);

    private static final BufferedReader  inputStream = new BufferedReader(new InputStreamReader(System.in));

    //private static TerminalReader instance;

    private static BaseHandler[] handlers;

   public TerminalReader() {

        handlers=new BaseHandler[3];

        handlers[2] = new EndCommandChain();

        handlers[1] = new ValidateCommandArgsHandler();
        handlers[1].setNext(handlers[2]);

        handlers[0] = new ValidateCommandTypeHandler();
        handlers[0].setNext(handlers[1]);


    }

    //public static TerminalReader getInstance(){

       // if(instance==null){

            //instance= new TerminalReader();

        //}

       // return instance;

    //}

    public Command inputCommand() throws IOException {

        Command result=new Command();
        String[] inputCommand;
        {

            String string = inputStream.readLine();
            logger.debug("Input: {}" ,string);
            inputCommand=string.split(" ");

        }
        result.setArguments(inputCommand, true);
        return result;

    }

    public Command getValidatedCommand(Command command)  throws IllegalArgumentException, IOException{

        return handlers[0].handle(command);

    }

    public void execute(){

        ApplicationContext context = new FileSystemXmlApplicationContext("src\\main\\resources\\application-beans.xml");
        ConsoleOutput consoleOutput = context.getBean("ConsoleOutput",ConsoleOutput.class);
        boolean open = true;

        while(open){

            Command command=null;

            try{

                command=getValidatedCommand(inputCommand());

            }
            catch (IOException | IllegalArgumentException exception){

                consoleOutput.output(exception.getMessage());
                logger.error(exception.getMessage());
                continue;

            }


            if(command.getType()==CommandType.QUIT){

                open = false;

            }

            try {

                context.getBean("TaskListController",TaskListController.class).performCommand(command);

            }
            catch (IllegalArgumentException illegalArgumentException){

                consoleOutput.output(illegalArgumentException.getMessage());
                logger.error(illegalArgumentException.getMessage());

            }

        }

    }

}
