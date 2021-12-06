package kalchenko.task;

public class Task {

    private int id;

    private boolean state = false;

    private String description;

    public Task(){}

    public Task(int id, boolean state,String description) {

        this.id=id;

        this.description = description;

    }

    public Task(int id,String description) {

        this.id=id;
        this.description = description;

    }


    public int getId() {

        return id;

    }

    public void setId(int id) {

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

