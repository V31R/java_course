package kalchenko.task;

import kalchenko.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskList taskList;
    @Autowired
    public TaskController(TaskList taskList) {

        this.taskList = taskList;

    }

    @GetMapping("")
    private List<Task> getList(){

        return taskList.getTasks();

    }

    @PostMapping("/{description}")
    private void newTask(@PathVariable  String description){

        taskList.add(description);

    }


    @GetMapping("/{id}")
    private Task getTask(@PathVariable Integer id){

        return taskList.getById(id);

    }

    @DeleteMapping("/{id}")
    private void deleteTask(@PathVariable Integer id){

        taskList.delete(id);

    }

    @PatchMapping("/{id}")
    private void editToggleTask(@PathVariable Integer id,@RequestBody Task task){

        if(task.getState() ^ taskList.getById(id).getState()) {

            taskList.toggle(id);

        }

        taskList.edit(id, task.getDescription());

    }

}
