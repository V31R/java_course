package kalchenko.task;

import kalchenko.security.Users;
import kalchenko.taskDTOLayer.TaskDTO;
import kalchenko.taskDTOLayer.TaskServiceImpl;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {

    private final TaskServiceImpl localService;



    public TaskController(TaskServiceImpl localService) {

        this.localService = localService;

    }

    @GetMapping("")
    public List<TaskDTO> getList(@AuthenticationPrincipal Users user){

        return localService.findAllByUserId(user).stream().toList();

    }

    @PostMapping("/{description}")
    public TaskDTO newTask(@PathVariable("description") @NotBlank String description, @AuthenticationPrincipal Users user){

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDescription(description);
        return localService.save(taskDTO,user);

    }


    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable("id") @NotBlank String id, @AuthenticationPrincipal Users user){

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(id);
        return localService.findByUserId(taskDTO, user);

    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") @NotBlank String id, @AuthenticationPrincipal Users user){

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(id);
        localService.deleteById(taskDTO,user);

    }

    @PutMapping("")
    public void editToggleTask( @RequestBody @Valid @NotNull TaskDTO taskDTO,
                               @AuthenticationPrincipal Users user){

        TaskDTO findedtask = localService.findByUserId(taskDTO, user);
        findedtask.setDescription(taskDTO.getDescription());
        findedtask.setDone(taskDTO.isDone());
        localService.save(findedtask,user);

    }

}
