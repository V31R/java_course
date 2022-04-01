package kalchenko.taskDTOLayer;

import kalchenko.task.Task;
import org.mapstruct.Mapper;

@Mapper
public interface TaskMapper {

    Task toTask(TaskDTO taskDTO);

    TaskDTO toTaskDTO(Task task);

}
