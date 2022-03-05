package kalchenko;

import kalchenko.security.UsersController;
import kalchenko.task.TaskController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeApplicationTest {

    @Autowired
    private UsersController usersController;

    @Autowired
    private TaskController taskController;

    @Test
    public void usersControllerLoad(){

        assertNotNull(usersController);

    }

    @Test
    public void taskControllerLoad(){

        assertNotNull(taskController);

    }

}
