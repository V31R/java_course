package kalchenko.commandStrategy;
import kalchenko.command.Command;
import kalchenko.task.TaskList;

public interface CommandStrategy {

    void execute(TaskList taskList, Command command);

}
