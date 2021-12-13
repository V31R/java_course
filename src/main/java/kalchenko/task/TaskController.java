package kalchenko.task;

import kalchenko.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
    private List<Task> getList(){

        return taskList.getTasks();

    }

    @PostMapping("/{description}")
    private void newTask(@PathVariable("description") @NotBlank String description){

        taskList.add(description);

    }


    @GetMapping("/{id}")
    private Task getTask(@PathVariable("id") @Min(1) Integer id){
        ResponseEntity.ok();
        return taskList.getById(id);

    }

    @DeleteMapping("/{id}")
    private void deleteTask(@PathVariable("id") @Min(1) Integer id){

        taskList.delete(id);

    }

    @PatchMapping("/{id}")
    private void editToggleTask(@PathVariable("id") @Min(1) Integer id, @Valid @RequestBody Task task){

        if(task.getState() ^ taskList.getById(id).getState()) {

            taskList.toggle(id);

        }

        taskList.edit(id, task.getDescription());

    }

}
