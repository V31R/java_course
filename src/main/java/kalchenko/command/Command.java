package kalchenko.command;

public class Command {

    private CommandType type;
    private String[] arguments;

    public Command(CommandType type) {

        this.type = type;

    }
    public Command(CommandType type, String[] arguments) {

        this.type = type;
        this.arguments = arguments;

    }

    public CommandType getType() {

        return type;

    }


    public void setType(CommandType type) {

        this.type = type;

    }

    public String[] getArguments() {

        return arguments;

    }

    public void setArguments(String[] arguments) {

        this.arguments = arguments;

    }

}
