import kalchenko.security.Users;
import kalchenko.task.Task;
import kalchenko.task.TaskCRUD;
import kalchenko.task.TaskController;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Suite
public class TaskControllerTest {

    @Test
    public void testGetList(){

        Optional<Task> optional = Optional.of(new Task());
        List<Task> data = new ArrayList<>();
        data.add(new Task());

        TaskCRUD taskCRUDMock = Mockito.mock(TaskCRUD.class);
        Mockito.when(taskCRUDMock.findAllByUserId(Mockito.anyInt())).thenReturn(optional);

        Users userMock= Mockito.mock(Users.class);

        TaskController taskController = new TaskController(taskCRUDMock);


        assertEquals(data.get(0).getId(),taskController.getList(userMock).get(0).getId());

    }

    @Test
    public void testPostTask(){

        String testString = "test";
        Task task = new Task(testString);
        Task taskSpy = Mockito.spy(task);

        TaskCRUD taskCRUDMock = Mockito.mock(TaskCRUD.class);
        Mockito.when(taskCRUDMock.save(Mockito.any())).thenReturn(taskSpy);

        Users userMock = Mockito.mock(Users.class);

        TaskController taskController = new TaskController(taskCRUDMock);

        assertEquals(testString,taskController.newTask(testString, userMock).getDescription());

    }

    @Test
    public void testGetById(){

        Users userMock= Mockito.mock(Users.class);

        Task task =new Task();
        task.setId(0L);
        task.setUser(userMock);

        Task taskSpy = Mockito.spy(task);

        Optional<Task> optional = Optional.of(task);

        TaskCRUD taskCRUDMock = Mockito.mock(TaskCRUD.class);
        Mockito.when(taskCRUDMock.findByUserId(taskSpy.getId(), userMock.getUserID())).thenReturn(optional);

        TaskController taskController = new TaskController(taskCRUDMock);

        assertEquals(task.getId(),taskController.getTask(taskSpy.getId(), userMock).getId());

    }


}
