package kalchenko.taskDTOLayer;


import kalchenko.exception.TaskNotFoundException;
import kalchenko.security.Users;
import kalchenko.task.Task;
import kalchenko.task.TaskCRUD;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    private final TaskCRUD taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskCRUD taskRepository, TaskMapper taskMapper) {

        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;

    }


    @Override
    public List<TaskDTO> findAllByUserId(Users users) {

        List<TaskDTO> result = new ArrayList<>();
        var tasks = taskRepository.findAllByUserId(users.getUserID()).stream().toList();
        for(var task : tasks){

            result.add(taskMapper.toTaskDTO(task));

        }
        return result;

    }

    @Override
    public TaskDTO findByUserId(TaskDTO taskDTO, Users users) {

        return taskMapper.toTaskDTO(
                taskRepository.findByUserId(Long.valueOf(taskDTO.getId()), users.getUserID())
                .orElseThrow(
                        ()-> new TaskNotFoundException(Long.valueOf(taskDTO.getId()))
                )
        );

    }

    @Override
    public TaskDTO save(TaskDTO taskDTO, Users users) {

        Task newTask = taskMapper.toTask(taskDTO);
        newTask.setUser(users);
        return taskMapper.toTaskDTO(taskRepository.save(newTask));

    }

    @Override
    public void deleteById(TaskDTO taskDTO, Users users) {

        Task task = taskRepository.findByUserId(Long.valueOf(taskDTO.getId()), users.getUserID())
                .orElseThrow(()-> new TaskNotFoundException(Long.valueOf(taskDTO.getId())));
        taskRepository.deleteById(task.getId());

    }

}
