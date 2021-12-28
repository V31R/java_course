package kalchenko.task;

import javax.validation.constraints.NotBlank;

public class MiniTask {

    private boolean done;

    @NotBlank
    private String description;

    MiniTask(){};

    public boolean isDone() {

        return done;

    }

    public void setDone(boolean done) {

        this.done = done;

    }

    public String getDescription() {

        return description;

    }

    public void setDescription(String description) {

        this.description = description;

    }

}
