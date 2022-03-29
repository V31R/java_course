package kalchenko.external;

import kalchenko.taskDTOLayer.TaskDTO;
import kalchenko.taskDTOLayer.TaskMapper;
import kalchenko.taskDTOLayer.TaskMapperImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExternalTaskMapperTest {

    private static String idString="1";
    private static Long id = Long.valueOf(1);
    private static String description = "description";

    @Test
    void taskToTaskDTO_Test(){

        ExternalTask task = getExternalTask();
        ExternalTaskMapper taskMapper = new ExternalTaskMapperImpl();

        TaskDTO taskDTO = taskMapper.toDTO(task);

        assertEquals(task.getName(),taskDTO.getDescription());
        assertEquals(task.getId(), Long.valueOf(taskDTO.getId()));
        assertEquals(task.isCompleted(), taskDTO.isDone());

    }

    @Test
    void taskDTOToTask_Test(){

        TaskDTO taskDTO = getTaskDTO();
        ExternalTaskMapper taskMapper = new ExternalTaskMapperImpl();

        ExternalTask externalTask = taskMapper.toExternalTask(taskDTO);

        assertEquals(taskDTO.getDescription(),externalTask.getName());
        assertEquals(taskDTO.getId(), String.valueOf(externalTask.getId()));
        assertEquals(taskDTO.isDone(), externalTask.isCompleted());

    }

    private static ExternalTask getExternalTask(){

        ExternalTask externalTask = new ExternalTask();
        externalTask.setName(description);
        externalTask.setId(id);
        externalTask.setCompleted(true);

        return externalTask;

    }

    private static TaskDTO getTaskDTO(){

        TaskDTO task = new TaskDTO();
        task.setDescription(description);
        task.setId(idString);
        task.setDone(true);

        return task;

    }

}
