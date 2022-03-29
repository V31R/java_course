package kalchenko.taskDTOLayer;


import kalchenko.task.Task;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import static org.junit.jupiter.api.Assertions.*;

@Suite
public class TaskMapperTest {

    private static String idString="1";
    private static Long id = Long.valueOf(1);
    private static String description = "description";

    @Test
    void taskToTaskDTO_Test(){

        Task task = getTask();
        TaskMapper taskMapper = new TaskMapperImpl();

        TaskDTO taskDTO = taskMapper.toTaskDTO(task);

        assertEquals(task.getDescription(),taskDTO.getDescription());
        assertEquals(task.getId(), Long.valueOf(taskDTO.getId()));
        assertEquals(task.isDone(), taskDTO.isDone());

    }

    @Test
    void taskDTOToTask_Test(){

        TaskDTO taskDTO = getTaskDTO();
        TaskMapper taskMapper = new TaskMapperImpl();

        Task task = taskMapper.toTask(taskDTO);

        assertEquals(taskDTO.getDescription(),task.getDescription());
        assertEquals(taskDTO.getId(), String.valueOf(task.getId()));
        assertEquals(taskDTO.isDone(), task.isDone());

    }

    private static Task getTask(){

        Task task = new Task(description);
        task.setId(id);
        task.setDone(true);

        return task;

    }

    private static TaskDTO getTaskDTO(){

        TaskDTO task = new TaskDTO();
        task.setDescription(description);
        task.setId(idString);
        task.setDone(true);

        return task;

    }

}
