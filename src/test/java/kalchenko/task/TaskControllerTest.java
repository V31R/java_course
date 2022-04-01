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

import java.util.ArrayList;
import java.util.List;
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

        List<TaskDTO> list = new ArrayList<>();
        list.add(taskDTO);
        Mockito.when(localServiceMock.findAllByUserId(Mockito.any(Users.class)))
                .thenReturn(list);

        Mockito.when(localServiceMock.findByUserId(Mockito.any(TaskDTO.class),
                Mockito.any(Users.class))).thenReturn(taskDTO);

        externalServiceMock = Mockito.mock(TaskServiceExternal.class);

        taskDTOExt = getTaskDTOExt();

        list = new ArrayList<>();
        list.add(taskDTOExt);

        Mockito.when(externalServiceMock.findAllByUserId(Mockito.any(Users.class)))
                .thenReturn(list);

        Mockito.when(externalServiceMock.findByUserId(Mockito.any(TaskDTO.class),
                Mockito.any(Users.class))).thenReturn(taskDTOExt);

        taskController = new TaskController(localServiceMock,externalServiceMock);

    }

    @Test
    public void testGetList_Local(){

        Mockito.when(externalServiceMock.findAllByUserId(Mockito.any(Users.class)))
                .thenReturn(new ArrayList<>());

        taskController.getList(user);

        Mockito.verify(localServiceMock).findAllByUserId(Mockito.any(Users.class));

    }

    @Test
    public void testGetList_External(){

        Mockito.when(localServiceMock.findAllByUserId(Mockito.any(Users.class)))
                .thenReturn(new ArrayList<>());

        taskController.getList(user);

        Mockito.verify(localServiceMock).findAllByUserId(Mockito.any(Users.class));

    }

    @Test
    public void testGetList_ALL(){

        taskController.getList(user);

        Mockito.verify(localServiceMock).findAllByUserId(Mockito.any(Users.class));
        Mockito.verify(externalServiceMock).findAllByUserId(Mockito.any(Users.class));

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

    @Test
    public void testDeleteTask_IfFound_Local(){

        taskController.deleteTask("1", user);

        Mockito.verify(localServiceMock).deleteById(Mockito.any(TaskDTO.class),Mockito.any(Users.class));

    }

    @Test
    public void testDeleteTask_IfFound_External(){

        taskController.deleteTask("EXT1", user);

        Mockito.verify(externalServiceMock).deleteById(Mockito.any(TaskDTO.class),Mockito.any(Users.class));

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

    @Test
    public void testEditToggleTask_IfFound_Local(){

        taskController.editToggleTask(taskDTO, user);

        Mockito.verify(localServiceMock).findByUserId(Mockito.any(TaskDTO.class),Mockito.any(Users.class));

    }

    @Test
    public void testEditToggleTask_IfFound_Save_Local(){

        taskController.editToggleTask(taskDTO, user);

        Mockito.verify(localServiceMock).save(Mockito.any(TaskDTO.class),Mockito.any(Users.class));

    }



    @Test
    public void testEditToggleTask_NotFound_Local(){

        setLocalTaskServiceMockForException();

        try {

            taskController.editToggleTask(taskDTO, user);
            fail("Expected TaskNotFoundException");

        }
        catch (TaskNotFoundException taskNotFoundException) {

            assertNotNull(taskNotFoundException);

        }

    }

    @Test
    public void testEditToggleTask_IfFound_External(){

        taskController.editToggleTask(taskDTOExt, user);

        Mockito.verify(externalServiceMock).findByUserId(Mockito.any(TaskDTO.class),Mockito.any(Users.class));

    }

    @Test
    public void testEditToggleTask_IfFound_Save_External(){

        taskController.editToggleTask(taskDTOExt, user);

        Mockito.verify(externalServiceMock).save(Mockito.any(TaskDTO.class),Mockito.any(Users.class));

    }



    @Test
    public void testEditToggleTask_NotFound_External(){

        setExternalTaskServiceMockForException();

        try {

            taskController.editToggleTask(taskDTOExt, user);
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

        Mockito.doThrow(new TaskNotFoundException(1))
                .when(localServiceMock).deleteById(Mockito.any(TaskDTO.class),Mockito.any(Users.class));


    }

    private void setExternalTaskServiceMockForException(){

        Mockito.when(externalServiceMock.findByUserId(Mockito.any(TaskDTO.class),Mockito.any(Users.class)))
                .thenThrow(new TaskNotFoundException(1));

    }

}
