package kalchenko.task;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Validated
public class Task {

    @Min(1)
    private int id;

    private boolean state = false;

    @NotBlank
    private String description;

    public Task(){}

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

