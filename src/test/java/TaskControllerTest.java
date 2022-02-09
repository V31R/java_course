import kalchenko.security.Users;
import kalchenko.task.Task;
import kalchenko.task.TaskCRUD;
import kalchenko.task.TaskController;
import org.assertj.core.internal.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Suite
public class TaskControllerTest {

    @Test
    public void testGet(){


        Optional<Task> optional=Optional.of(new Task());
        List<Task> data = new ArrayList<>();
        data.add(new Task());

        TaskCRUD taskCRUDMock = Mockito.mock(TaskCRUD.class);
        Mockito.when(taskCRUDMock.findAllByUserId(0)).thenReturn(optional);

        TaskController taskController = new TaskController(taskCRUDMock);

        Users userMock= Mockito.mock(Users.class);

        assertEquals(data.get(0).getId(),taskController.getList(userMock).get(0).getId());

    }

    @Test
    public void testPost(){

        String testString = new String("test");

        Task task = new Task();
        Task taskSpy = Mockito.spy(task);
        Mockito.when(taskSpy.getDescription()).thenReturn(testString);

        TaskCRUD taskCRUDMock = Mockito.mock(TaskCRUD.class);
        Mockito.when(taskCRUDMock.save(taskSpy)).thenReturn(taskSpy);
        //Mockito.when(taskCRUDMock.save(taskSpy)).thenReturn(new Task(testString));

        TaskController taskController = new TaskController(taskCRUDMock);

        Users userMock= Mockito.mock(Users.class);

        assertEquals(testString,taskController.newTask(testString, userMock).getDescription());

    }


}
