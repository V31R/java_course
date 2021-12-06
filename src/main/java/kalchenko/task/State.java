package kalchenko.task;

public class State {

    boolean state = false;

    public State(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
