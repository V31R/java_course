package kalchenko.program;

import kalchenko.input_class.TerminalReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


@SpringBootApplication
public class Main implements CommandLineRunner {

    public static  void  main(String[] args){

        SpringApplication.run(Main.class,args);

    }

    @Override
    public void run(String... args) throws Exception {

        ApplicationContext context =
                new FileSystemXmlApplicationContext("src\\main\\resources\\application-beans.xml");

        TerminalReader terminalReader=context.getBean("TerminalReader",TerminalReader.class);

        terminalReader.execute();

    }

}
