package kalchenko.task;

import kalchenko.exception.TaskNotFoundException;
import kalchenko.security.Users;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    private final TaskCRUD taskRepository;

    public TaskController(TaskCRUD taskRepository) {

        this.taskRepository = taskRepository;

    }

    @GetMapping("")
    public List<Task> getList(@AuthenticationPrincipal Users user){

        return taskRepository.findAllByUserId(user.getUserID()).stream().toList();

    }

    @PostMapping("/{description}")
    public Task newTask(@PathVariable("description") @NotBlank String description, @AuthenticationPrincipal Users user){

        Task newTask = new Task(description);
        newTask.setUser(user);
        return taskRepository.save(newTask);


    }


    @GetMapping("/{id}")
    public Task getTask(@PathVariable("id") @Min(1) Long id, @AuthenticationPrincipal Users user){

        return taskRepository.findByUserId(id, user.getUserID())
                .orElseThrow(()-> new TaskNotFoundException(id));

    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") @Min(1) Long id, @AuthenticationPrincipal Users user){

        Task task=taskRepository.findByUserId(id, user.getUserID())
                .orElseThrow(()-> new TaskNotFoundException(id));
        taskRepository.deleteById(task.getId());

    }

    @PatchMapping("/{id}")
    public void editToggleTask(@PathVariable("id") @Min (1) Long id, @RequestBody @Valid @NotNull Task task,
                               @AuthenticationPrincipal Users user){

        Task findedtask = taskRepository.findByUserId(id, user.getUserID())
                .orElseThrow(()-> new TaskNotFoundException(id));

        task.setId(id);
        task.setUser(findedtask.getUser());

        taskRepository.save(task);

    }

}
