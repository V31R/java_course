package kalchenko.task;

import kalchenko.exception.TaskNotFoundException;
import kalchenko.external.TaskServiceExternal;
import kalchenko.security.Users;

import kalchenko.taskDTOLayer.TaskDTO;
import kalchenko.taskDTOLayer.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Suite
public class TaskControllerTest {

    private TaskServiceImpl localServiceMock;
    private TaskServiceExternal externalServiceMock;
    private TaskController taskController;
    private Users user;
    private TaskDTO taskDTO;
    private TaskDTO taskDTOExt;
    @BeforeEach
    public void setUp(){

        user = getUser();
        taskDTO = getTaskDTO();
        localServiceMock = Mockito.mock(TaskServiceImpl.class);

        Optional<TaskDTO> optional = Optional.of(getTaskDTO());

        Mockito.when(localServiceMock.findAllByUserId(Mockito.any(Users.class)))
                .thenReturn(optional.stream().toList());

        Mockito.when(localServiceMock.findByUserId(Mockito.any(TaskDTO.class),
                Mockito.any(Users.class))).thenReturn(taskDTO);

        externalServiceMock = Mockito.mock(TaskServiceExternal.class);

        taskDTOExt = getTaskDTOExt();

        optional = Optional.of(getTaskDTOExt());

        Mockito.when(externalServiceMock.findAllByUserId(Mockito.any(Users.class)))
                .thenReturn(optional.stream().toList());

        Mockito.when(externalServiceMock.findByUserId(Mockito.any(TaskDTO.class),
                Mockito.any(Users.class))).thenReturn(taskDTOExt);

        taskController = new TaskController(localServiceMock,externalServiceMock);

    }

    @Test
    public void testGetList_Local(){

        taskController.getList(user);

        Mockito.verify(localServiceMock).findAllByUserId(Mockito.any(Users.class));

    }

    @Test
    public void testNewTask_Local(){

        taskController.newTask("",user);

        Mockito.verify(localServiceMock).save(Mockito.any(TaskDTO.class),Mockito.any(Users.class));

    }


    @Test
    public void testGetTask_Local_IfFound(){

        var result = taskController.getTask("1", user);

        Mockito.verify(localServiceMock).findByUserId(Mockito.any(TaskDTO.class),Mockito.any(Users.class));

    }



    @Test
    public void testGetTask_Local_NotFound(){

        setLocalTaskServiceMockForException();

        try {

            taskController.getTask("1", user);
            fail("Expected TaskNotFoundException");

        }
        catch (TaskNotFoundException taskNotFoundException) {

            assertNotNull(taskNotFoundException);

        }

    }

    @Test
    public void testGetTask_External_NotFound(){

        setExternalTaskServiceMockForException();

        try {

            taskController.getTask("EXT1", user);
            fail("Expected TaskNotFoundException");

        }
        catch (TaskNotFoundException taskNotFoundException) {

            assertNotNull(taskNotFoundException);

        }

    }
    /*
    @Test
    public void testDeleteTask_IfFound_Local(){

        taskController.deleteTask("1", user);

        Mockito.verify(localServiceMock).findByUserId(Mockito.any(TaskDTO.class),Mockito.any(Users.class));

    }

    @Test
    public void testDeleteTask_IfFound_Delete_Local(){

        taskController.deleteTask("1", user);

        Mockito.verify(localServiceMock).deleteById(Mockito.any(TaskDTO.class),Mockito.any(Users.class));

    }

    @Test
    public void testDeleteTask_NotFound_Local(){

        setLocalTaskServiceMockForException();

        try {

            taskController.deleteTask("0", user);
            fail("Expected TaskNotFoundException");

        }
        catch (TaskNotFoundException taskNotFoundException) {

            assertNotNull(taskNotFoundException);

        }

    }
    */
    /*
    @Test
    public void testEditToggleTask_IfFound(){

        taskController.editToggleTask(1L, task, user);

        Mockito.verify(localServiceMock).findByUserId(Mockito.anyInt(),Mockito.anyInt());

    }

    @Test
    public void testEditToggleTask_IfFound_SetID(){

        Long testId = Long.valueOf(1);

        taskController.editToggleTask(testId, task, user);

        assertEquals(testId, task.getId());

    }

    @Test
    public void testEditToggleTask_IfFound_SetUser(){

        taskController.editToggleTask(1L, task, user);

        assertEquals(task.getUser().getUserID(), user.getUserID());
        assertEquals(task.getUser().getUsername(), user.getUsername());
        assertEquals(task.getUser().getPassword(), user.getPassword());

    }

    @Test
    public void testEditToggleTask_IfFound_Save(){

        taskController.editToggleTask(1L, task, user);

        Mockito.verify(localServiceMock).save(Mockito.any(Task.class));

    }

    @Test
    public void testEditToggleTask_NotFound(){

        setLocalTaskServiceMockForException();

        try {

            taskController.editToggleTask(1L, task, user);
            fail("Expected TaskNotFoundException");

        }
        catch (TaskNotFoundException taskNotFoundException) {

            assertNotNull(taskNotFoundException);

        }

    }
    */

    private static Users getUser(){

        Users user = new Users();
        user.setUserID(1L);
        user.setName("User");
        user.setPassword("password");

        return  user;

    }

    private static TaskDTO getTaskDTO(){

        TaskDTO task = new TaskDTO();
        task.setDescription("");
        task.setId("1");

        return task;

    }

    private static TaskDTO getTaskDTOExt(){

        TaskDTO task = new TaskDTO();
        task.setDescription("");
        task.setId("EXT1");

        return task;

    }

    private void setLocalTaskServiceMockForException(){

        Mockito.when(localServiceMock.findByUserId(Mockito.any(TaskDTO.class),Mockito.any(Users.class)))
                .thenThrow(new TaskNotFoundException(1));

    }

    private void setExternalTaskServiceMockForException(){

        Mockito.when(externalServiceMock.findByUserId(Mockito.any(TaskDTO.class),Mockito.any(Users.class)))
                .thenThrow(new TaskNotFoundException(1));

    }

}
