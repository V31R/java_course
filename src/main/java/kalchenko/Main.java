package kalchenko;

import kalchenko.input_class.TerminalReader;
import kalchenko.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;


@SpringBootApplication
public class Main{

    @Autowired
    private TerminalReader terminalReader;

    public static  void  main(String[] args){

        SpringApplication.run(Main.class,args);

    }


}
