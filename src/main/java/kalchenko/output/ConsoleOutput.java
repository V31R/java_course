package kalchenko.output;

import kalchenko.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class ConsoleOutput implements BaseOutput {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutput.class);

    public ConsoleOutput()
    {}

    public void output(String message){

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

        ApplicationContext context = new
                FileSystemXmlApplicationContext("src\\main\\resources\\application-beans.xml");
        context.getBean("ConsoleOutput",ConsoleOutput.class).output(stringBuilder.toString());

    }


}
