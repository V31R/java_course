package kalchenko.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {

    private final TaskList taskList;
    @Autowired
    public TaskController(TaskList taskList) {

        this.taskList = taskList;

    }

    @GetMapping("")
    public List<Task> getList(){

        return taskList.getTasks();

    }

    @PostMapping("/{description}")
    public void newTask(@PathVariable("description") @NotBlank String description){

        taskList.add(description);

    }


    @GetMapping("/{id}")
    public Task getTask(@PathVariable("id") @Min(1) Integer id){

        return taskList.getById(id);

    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") @Min(1) Integer id){

        taskList.delete(id);

    }

    @PatchMapping("/{id}")
    public void editToggleTask(@PathVariable("id") @Min (1) Integer id, @RequestBody @Valid @NotNull Task task){

        if(task.getState() ^ taskList.getById(id).getState()) {

            taskList.toggle(id);

        }

        taskList.edit(id, task.getDescription());

    }

}
