package kalchenko.output;

import kalchenko.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class ConsoleOutput{

    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutput.class);

    public static void output(String message){

        System.out.println(message);
        logger.debug("Output: {}", message);

    }

    static public void print_task(Map.Entry<Integer, Task> task){

        StringBuilder stringBuilder=new StringBuilder(task.getKey().toString());
        stringBuilder.append(". [")
                .append(task.getValue().getState() ? "x" : " ")
                .append("] ")
                .append(task.getValue().getDescription())
                .append("\n");

        ConsoleOutput.output(stringBuilder.toString());

    }

}
