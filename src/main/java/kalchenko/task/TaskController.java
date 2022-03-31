package kalchenko.task;

import kalchenko.external.TaskServiceExternal;
import kalchenko.security.Users;
import kalchenko.taskDTOLayer.TaskDTO;
import kalchenko.taskDTOLayer.TaskService;
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
    private final TaskServiceExternal externalService;
    private TaskService currentService;

    public TaskController(TaskServiceImpl localService,TaskServiceExternal externalService) {

        this.localService = localService;
        this.externalService = externalService;

    }

    @GetMapping("")
    public List<TaskDTO> getList(@AuthenticationPrincipal Users user){
        
        var tasks= localService.findAllByUserId(user);
        if(tasks.size() == 0){

            tasks = externalService.findAllByUserId(user);

        }

        return tasks;

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
        chooseService(id);
        
        return currentService.findByUserId(taskDTO, user);

    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") @NotBlank String id, @AuthenticationPrincipal Users user){

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(id);
        chooseService(id);

        currentService.deleteById(taskDTO,user);

    }

    @PutMapping("")
    public void editToggleTask( @RequestBody @Valid @NotNull TaskDTO taskDTO,
                               @AuthenticationPrincipal Users user){

        chooseService(taskDTO.getId());
        
        TaskDTO findedTask = currentService.findByUserId(taskDTO, user);
        findedTask.setDescription(taskDTO.getDescription());
        findedTask.setDone(taskDTO.isDone());
        currentService.save(findedTask,user);

    }

    private TaskService chooseService(String id){

        try {
            var l = Long.valueOf(id);
            currentService = localService;
        }
        catch (NumberFormatException numberFormatException){

            currentService = externalService;

        }

        return  currentService;

    }

}
