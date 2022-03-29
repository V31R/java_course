package kalchenko.exception;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import static org.junit.jupiter.api.Assertions.*;

@Suite
public class ExceptionTest {

    @Test
    void checkMessage(){

        TaskNotFoundException exception = new TaskNotFoundException(100);
        assertEquals("Could not find task 100",exception.getMessage());

    }

    @Test
    void checkAdvice(){

        TaskNotFoundException exception = new TaskNotFoundException(100);
        TaskNotFoundExceptionAdvice advice = new TaskNotFoundExceptionAdvice();
        assertEquals("Could not find task 100",advice.taskNotFoundHandler(exception));

    }

}
