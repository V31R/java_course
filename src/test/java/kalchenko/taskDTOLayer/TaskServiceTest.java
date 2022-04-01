package kalchenko.taskDTOLayer;

import kalchenko.exception.TaskNotFoundException;
import kalchenko.security.Users;

import kalchenko.task.Task;
import kalchenko.task.TaskCRUD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskServiceTest {

    private TaskCRUD taskRepositoryMock;
    private TaskService taskService;
    private Users user;
    private TaskDTO taskDTO;
    private static TaskMapper taskMapper = new TaskMapperImpl();

    @BeforeEach
    public void setUp(){

        user = getUser();
        taskDTO = getTaskDTO();
        taskRepositoryMock = Mockito.mock(TaskCRUD.class);

        Optional<Task> optional = Optional.of(getTask());

        Mockito.when(taskRepositoryMock.findAllByUserId(Mockito.anyInt()))
                .thenReturn(optional);

        Mockito.when(taskRepositoryMock.findByUserId(Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(optional);

        taskService = new TaskServiceImpl(taskRepositoryMock,taskMapper);

    }

    @Test
    public void testFindAllByUserId(){

        taskService.findAllByUserId(user);

        Mockito.verify(taskRepositoryMock).findAllByUserId(Mockito.anyInt());

    }

    @Test
    public void testSave(){

        taskService.save(taskDTO,user);

        Mockito.verify(taskRepositoryMock).save(Mockito.any(Task.class));

    }


    @Test
    public void testFindByUserId_IfFound(){

        taskService.findByUserId(taskDTO, user);

        Mockito.verify(taskRepositoryMock).findByUserId(Mockito.anyInt(),Mockito.anyInt());

    }



    @Test
    public void testFindByUserId_NotFound(){

        setTaskRepositoryMockForException();

        try {

            taskService.findByUserId(taskDTO, user);
            fail("Expected TaskNotFoundException");

        }
        catch (TaskNotFoundException taskNotFoundException) {

            assertNotNull(taskNotFoundException);

        }

    }

    @Test
    public void testDeleteById_IfFound(){

        taskService.deleteById(taskDTO, user);

        Mockito.verify(taskRepositoryMock).findByUserId(Mockito.anyInt(),Mockito.anyInt());

    }

    @Test
    public void testDeleteById_IfFound_Delete(){

        taskService.deleteById(taskDTO, user);

        Mockito.verify(taskRepositoryMock).deleteById(Mockito.anyLong());

    }

    @Test
    public void testDeleteTask_NotFound(){

        setTaskRepositoryMockForException();

        try {

            taskService.deleteById(taskDTO, user);
            fail("Expected TaskNotFoundException");

        }
        catch (TaskNotFoundException taskNotFoundException) {

            assertNotNull(taskNotFoundException);

        }

    }

    private static Users getUser(){

        Users user = new Users();
        user.setUserID(1L);
        user.setName("User");
        user.setPassword("password");

        return  user;

    }

    private static Task getTask(){

        Task task = new Task("");
        task.setUser(getUser());
        task.setId(1L);

        return task;

    }

    private static TaskDTO getTaskDTO(){

        TaskDTO task = new TaskDTO();
        task.setDescription("");
        task.setId("1");

        return task;

    }

    private void setTaskRepositoryMockForException(){

        Mockito.when(taskRepositoryMock.findByUserId(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Optional.empty());

    }

}

