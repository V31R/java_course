package kalchenko.task;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskList {

    private int current_id;

    //private LinkedHashMap<Integer, Task> tasks;

    private List<Task> tasks;

    public TaskList() {

        this.tasks = new ArrayList<Task>();
        this.current_id = 1;

    }

    public void add(String description){

        tasks.add(new Task(current_id++,description));
        //tasks.put(current_id++,new Task(description));

    }


    public void toggle(int index){

        tasks.get(index).toggleState();

    }
    public void delete(int index){

        //Task toDelete=tasks.stream().findFirst().filter((t)->t.getId()==index).get();

        tasks.removeIf((t)->t.getId()==index);

    }

    public void edit(Integer index, String newDescription){

        tasks.get(index).setDescription(newDescription);

    }

    public List<Task> getTasks() {

        return tasks;

    }

}