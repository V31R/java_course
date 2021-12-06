package kalchenko.task;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskList {

    private int current_id;

    private List<Task> tasks;

    public TaskList() {

        this.tasks = new ArrayList<Task>();
        this.current_id = 1;

    }

    public void add(String description){

        tasks.add(new Task(current_id++,description));

    }


    public void toggle(int index){

        tasks.stream()
                .filter((t)->t.getId()==index)
                .findFirst()
                .get()
                .toggleState();

    }
    public void delete(int index){

        tasks.removeIf((t)->t.getId()==index);

    }

    public void edit(Integer index, String newDescription){

        tasks.stream()
                .filter((t)->t.getId()==index).findFirst()
                .get()
                .setDescription(newDescription);

    }

    public List<Task> getTasks() {

        return tasks;

    }

}