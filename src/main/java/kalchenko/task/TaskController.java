package kalchenko.task;

import kalchenko.output.ConsoleOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskList taskList;
    @Autowired
    public TaskController(TaskList taskList) {

        this.taskList = taskList;
        this.taskList.add("New Task");

    }

    @GetMapping("/tasks")
    List<Task> getList(){

        return taskList.getTasks();

    }

    @PostMapping("/tasks")
    Task newTask(@RequestBody  Task task){
        ConsoleOutput.output("Input new task");
        taskList.add(task.getDescription());
        return task;
    }

    @PostMapping("/tasks/{description}")
    String newTask(@PathVariable  String description){
        ConsoleOutput.output("Input new task");
        taskList.add(description);
        return description;
    }


    @GetMapping("/tasks/{id}")
    Task task(@PathVariable Integer id){

        return taskList.getTasks().stream()
                .filter((t)->t.getId()==id.intValue())
                .findFirst()
                .get();

    }

    @DeleteMapping("/tasks/{id}")
    void deleteTask(@PathVariable Integer id){

        taskList.delete(id);

    }

    @PutMapping("/tasks")
    void editToggleTask(@RequestBody Task task){

        ConsoleOutput.output("Edited new task");
        if(task.getState()) {

            taskList.toggle(task.getId());

        }

        taskList.edit(task.getId(), task.getDescription());

    }
}
