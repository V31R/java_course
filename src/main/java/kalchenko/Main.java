package kalchenko;

import kalchenko.input_class.TerminalReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main implements CommandLineRunner{

    @Autowired
    private TerminalReader terminalReader;

    public static  void  main(String[] args){

        SpringApplication.run(Main.class,args);

    }

    @Override
    public void run(String... args) throws Exception {

        terminalReader.execute();

    }

}
