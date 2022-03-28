package kalchenko;

import kalchenko.task.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

@SpringBootApplication
public class Main{

    public static  void  main(String[] args){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user","1234");
        HttpEntity<Task[]> entity = new HttpEntity<Task[]>(headers);
        var result = restTemplate.exchange("http://localhost:54322/taskRest", HttpMethod.GET,entity,Task[].class);
        HttpStatus statusCode = result.getStatusCode();

        SpringApplication.run(Main.class, args);


    }

}
