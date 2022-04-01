package kalchenko.taskDTOLayer;

import kalchenko.security.Users;
import kalchenko.task.Task;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<TaskDTO> findAllByUserId(Users users);

    TaskDTO findByUserId(TaskDTO taskDTO, Users users);

    TaskDTO save(TaskDTO taskDTO, Users users);

    void deleteById(TaskDTO taskDTO, Users users);

}
