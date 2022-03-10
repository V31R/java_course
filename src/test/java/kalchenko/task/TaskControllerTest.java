package kalchenko.task;

import kalchenko.exception.TaskNotFoundException;
import kalchenko.security.Users;
import kalchenko.task.Task;
import kalchenko.task.TaskCRUD;
import kalchenko.task.TaskController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Suite
public class TaskControllerTest {

    private TaskCRUD taskRepositoryMock;
    private TaskController taskController;
    private Users user;
    private Task task;

    @BeforeEach
    public void setUp(){

        user = getUser();
        task = getTask();
        taskRepositoryMock = Mockito.mock(TaskCRUD.class);

        Optional<Task> optional = Optional.of(getTask());

        Mockito.when(taskRepositoryMock.findAllByUserId(Mockito.anyInt()))
                .thenReturn(optional);

        Mockito.when(taskRepositoryMock.findByUserId(Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(optional);

        taskController = new TaskController(taskRepositoryMock);

    }

    @Test
    public void testGetList(){

        taskController.getList(user);

        Mockito.verify(taskRepositoryMock).findAllByUserId(Mockito.anyInt());

    }

    @Test
    public void testNewTask(){

        taskController.newTask("",user);

        Mockito.verify(taskRepositoryMock).save(Mockito.any(Task.class));

    }


    @Test
    public void testGetTask_IfFound(){

        taskController.getTask(1L, user);

        Mockito.verify(taskRepositoryMock).findByUserId(Mockito.anyInt(),Mockito.anyInt());

    }



    @Test
    public void testGetTask_NotFound(){

        setTaskRepositoryMockForException();

        try {

            taskController.getTask(0L, user);
            fail("Expected TaskNotFoundException");

        }
        catch (TaskNotFoundException taskNotFoundException) {

            assertNotNull(taskNotFoundException);

        }

    }

    @Test
    public void testDeleteTask_IfFound(){

        taskController.deleteTask(1L, user);

        Mockito.verify(taskRepositoryMock).findByUserId(Mockito.anyInt(),Mockito.anyInt());

    }

    @Test
    public void testDeleteTask_IfFound_Delete(){

        taskController.deleteTask(1L, user);

        Mockito.verify(taskRepositoryMock).deleteById(Mockito.anyLong());

    }

    @Test
    public void testDeleteTask_NotFound(){

        setTaskRepositoryMockForException();

        try {

            taskController.deleteTask(0L, user);
            fail("Expected TaskNotFoundException");

        }
        catch (TaskNotFoundException taskNotFoundException) {

            assertNotNull(taskNotFoundException);

        }

    }


    @Test
    public void testEditToggleTask_IfFound(){

        taskController.editToggleTask(1L, task, user);

        Mockito.verify(taskRepositoryMock).findByUserId(Mockito.anyInt(),Mockito.anyInt());

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

        Mockito.verify(taskRepositoryMock).save(Mockito.any(Task.class));

    }

    @Test
    public void testEditToggleTask_NotFound(){

        setTaskRepositoryMockForException();

        try {

            taskController.editToggleTask(1L, task, user);
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

    private void setTaskRepositoryMockForException(){

        Mockito.when(taskRepositoryMock.findByUserId(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Optional.empty());

    }

}
