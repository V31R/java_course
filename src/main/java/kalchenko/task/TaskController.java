package kalchenko.task;

import kalchenko.security.Users;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {

        this.taskRepository = taskRepository;

    }

    @GetMapping("")
    public List<Task> getList(){

        return taskRepository.findAll();

    }

    @PostMapping("/{description}")
    public Task newTask(@PathVariable("description") @NotBlank String description, @AuthenticationPrincipal Users user){

        Task newTask=new Task(description);
        newTask.setUser(user);
        return taskRepository.save(newTask);


    }


    @GetMapping("/{id}")
    public Task getTask(@PathVariable("id") @Min(1) Long id){

        return taskRepository.getById(id);

    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") @Min(1) Long id){

        taskRepository.deleteById(id);

    }

    @PatchMapping("/{id}")
    public void editToggleTask(@PathVariable("id") @Min (1) Long id, @RequestBody @Valid @NotNull Task task){

        task.setId(id);
        taskRepository.save(task);

    }

}
