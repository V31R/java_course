package kalchenko.task;


import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TaskList {

    private int current_id;

    private LinkedHashMap<Integer, Task> tasks;


    public TaskList() {

        this.tasks = new LinkedHashMap<>();
        this.current_id = 1;

    }

    public void add(String description){

        tasks.put(current_id++,new Task(description));

    }

    public void toggle(int index){

        tasks.get(index).toggleState();

    }
    public void delete(int index){

        tasks.remove(index);

    }

    public void edit(Integer index, String newDescription){

        tasks.get(index).setDescription(newDescription);

    }

    public LinkedHashMap<Integer, Task> getTasks() {

        return tasks;

    }


}