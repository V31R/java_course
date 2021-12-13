package kalchenko.task;

import kalchenko.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Validated
public class TaskList {

    private int current_id;

    private List<Task> tasks;

    public TaskList() {

        this.tasks = new ArrayList<Task>();
        this.current_id = 1;

    }

    public Task getById(int index) {
        return tasks.stream()
                .filter((t)->t.getId()==index)
                .findFirst()
                .orElseThrow(()->new TaskNotFoundException(index));
    }

    public void add(String description){

        tasks.add(new Task(current_id++,description));

    }


    public void toggle(int index){

        this.getById(index).toggleState();

    }
    public void delete(int index){

        tasks.removeIf((t)->t.getId()==index);

    }

    public void edit(Integer index, String newDescription){

        this.getById(index).setDescription(newDescription);

    }

    public List<Task> getTasks() {

        return tasks;

    }

}