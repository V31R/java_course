package kalchenko.external;

import kalchenko.exception.TaskNotFoundException;
import kalchenko.security.Users;
import kalchenko.taskDTOLayer.TaskDTO;
import kalchenko.taskDTOLayer.TaskService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceExternal implements TaskService {

    RestTemplate restTemplate = new RestTemplate();
    static private  String  baseURL = "http://localhost:54322/taskRest/";
    private ExternalTaskMapper taskMapper = new ExternalTaskMapperImpl();

    @Override
    public List<TaskDTO> findAllByUserId(Users users) {

        HttpHeaders httpHeaders = getHeaderWithAuth(users);

        HttpEntity<ExternalTask[]> entity = new HttpEntity<ExternalTask[]>(httpHeaders);
        var response = restTemplate.exchange(baseURL, HttpMethod.GET,entity,ExternalTask[].class);
        HttpStatus statusCode = response.getStatusCode();
        if(statusCode.isError() || response.getBody() == null){

            throw  new TaskNotFoundException(0);

        }
        List<TaskDTO> result = new ArrayList<>();
        for (var task : response.getBody()){

            result.add(taskMapper.toDTO(task));

        }

        return result;

    }

    @Override
    public TaskDTO findByUserId(TaskDTO taskDTO, Users users) {

        HttpHeaders httpHeaders = getHeaderWithAuth(users);

        HttpEntity<ExternalTask> entity = new HttpEntity<ExternalTask>(httpHeaders);
        var response = restTemplate.exchange(baseURL+taskDTO.getId(), HttpMethod.GET,entity,ExternalTask.class);
        HttpStatus statusCode = response.getStatusCode();
        if(statusCode.isError() || response.getBody() == null){

            throw  new TaskNotFoundException(Long.valueOf(taskDTO.getId()));

        }

        return taskMapper.toDTO(response.getBody());

    }

    @Override
    public TaskDTO save(TaskDTO taskDTO, Users users) {

        HttpHeaders httpHeaders = getHeaderWithAuth(users);

        HttpEntity<ExternalTask> entity = new HttpEntity<ExternalTask>(httpHeaders);
        var response =
                restTemplate.exchange(baseURL+"add?name="+taskDTO.getDescription(),
                        HttpMethod.POST,entity,ExternalTask.class);
        HttpStatus statusCode = response.getStatusCode();
        if(statusCode.isError()){

            throw  new TaskNotFoundException(Long.valueOf(taskDTO.getId()));

        }

        return taskMapper.toDTO(response.getBody());

    }

    @Override
    public void deleteById(TaskDTO taskDTO, Users users) {

        HttpHeaders httpHeaders = getHeaderWithAuth(users);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        var response =
                restTemplate.exchange(baseURL+"delete/"+taskDTO.getId(),
                        HttpMethod.DELETE,entity,String.class);
        HttpStatus statusCode = response.getStatusCode();
        if(statusCode.isError()){

            throw  new TaskNotFoundException(Long.valueOf(taskDTO.getId()));

        }

    }

    private  HttpHeaders getHeaderWithAuth(Users user){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(user.getUsername(), user.getPassword());

        return  httpHeaders;

    }

}
