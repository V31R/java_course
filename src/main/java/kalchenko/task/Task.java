package kalchenko.task;

public class Task {



    private Integer id;

    private boolean state = false;

    private String description;

    public Task(Integer id,boolean state,String description) {

        this.description = description;

    }

    public Task(Integer id,String description) {

        this.description = description;

    }

    public Task(String description) {

        this.description = description;

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Task(Integer id) {
        this.id = id;
    }

    public void setState(boolean state) {

        this.state = state;

    }

    public boolean getState(){

        return state;

    }

    public void toggleState(){

        state = !state;

    }

    public void setDescription(String description){

        this.description=description;

    }

    public String getDescription(){

        return description;

    }

}

