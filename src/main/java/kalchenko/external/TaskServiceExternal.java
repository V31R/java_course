package kalchenko.external;

import kalchenko.exception.TaskNotFoundException;
import kalchenko.security.Users;
import kalchenko.taskDTOLayer.TaskDTO;
import kalchenko.taskDTOLayer.TaskService;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${to_do_list.external.host}")
    private String  host;

    @Value("${to_do_list.external.base_url}")
    private String baseURL;

    @Value("${to_do_list.external.port}")
    private int port ;

    @Value("${to_do_list.external.prefix}")
    private String prefix;

    final private ExternalTaskMapper taskMapper = new ExternalTaskMapperImpl();

    @Override
    public List<TaskDTO> findAllByUserId(Users users) {

        HttpHeaders httpHeaders = getHeaderWithAuth(users);

        HttpEntity<ExternalTask[]> entity = new HttpEntity<ExternalTask[]>(httpHeaders);
        var response = restTemplate.exchange(url(), HttpMethod.GET,entity,ExternalTask[].class);
        HttpStatus statusCode = response.getStatusCode();
        if(statusCode.isError() || response.getBody() == null){

            throw  new TaskNotFoundException(0);

        }
        List<TaskDTO> result = new ArrayList<>();
        for (var task : response.getBody()){

            result.add(getDTO(task));


        }

        return result;

    }

    @Override
    public TaskDTO findByUserId(TaskDTO taskDTO, Users users) {

        HttpHeaders httpHeaders = getHeaderWithAuth(users);
        HttpEntity<ExternalTask> entity = new HttpEntity<ExternalTask>(httpHeaders);

        taskDTO.setId(taskDTO.getId().substring(prefix.length()));
        var response = restTemplate.exchange(url()+taskDTO.getId(), HttpMethod.GET,entity,ExternalTask.class);
        HttpStatus statusCode = response.getStatusCode();
        if(statusCode.isError() || response.getBody() == null){

            throw  new TaskNotFoundException(Long.valueOf(taskDTO.getId()));

        }

        return getDTO(response.getBody());

    }

    @Override
    public TaskDTO save(TaskDTO taskDTO, Users users) {

        HttpHeaders httpHeaders = getHeaderWithAuth(users);

        HttpEntity<ExternalTask> entity = new HttpEntity<ExternalTask>(httpHeaders);
        var response =
                restTemplate.exchange(url()+"add?name="+taskDTO.getDescription(),
                        HttpMethod.POST,entity,ExternalTask.class);
        HttpStatus statusCode = response.getStatusCode();
        if(statusCode.isError()){

            throw  new TaskNotFoundException(Long.valueOf(taskDTO.getId()));

        }

        return getDTO(response.getBody());

    }

    @Override
    public void deleteById(TaskDTO taskDTO, Users users) {

        HttpHeaders httpHeaders = getHeaderWithAuth(users);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        taskDTO.setId(taskDTO.getId().substring(prefix.length()));
        var response =
                restTemplate.exchange(url()+"delete/"+taskDTO.getId(),
                        HttpMethod.DELETE,entity,String.class);
        HttpStatus statusCode = response.getStatusCode();
        if(statusCode.isError()){

            throw  new TaskNotFoundException(Long.valueOf(taskDTO.getId()));

        }

    }


    public void setPort(int port){

        this.port = port;

    }

    public int getPort() {

        return port;

    }

    private  HttpHeaders getHeaderWithAuth(Users user){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(user.getUsername(), user.getPassword());

        return  httpHeaders;

    }

    private TaskDTO getDTO(ExternalTask externalTask){

        var dto = taskMapper.toDTO(externalTask);
        dto.setId(prefix+dto.getId());

        return  dto;

    }

    private String url(){

        return host + port + baseURL;

    }

}
