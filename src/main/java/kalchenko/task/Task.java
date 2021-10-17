package kalchenko.task;

public class Task {

    private boolean state = false;
    private String description;

    public Task(String description) {

        this.description = description;

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

