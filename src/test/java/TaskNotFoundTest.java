import kalchenko.exception.TaskNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskNotFoundTest {

    @Test
    void checkMessage(){

        TaskNotFoundException exception = new TaskNotFoundException(100);
        assertEquals("Could not find task 100",exception.getMessage());
        assertNotEquals("Could nottask 100",exception.getMessage());

    }

}
