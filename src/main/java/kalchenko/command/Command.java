package kalchenko.command;

public class Command {

    private CommandType type;
    private String argument;

    public Command(CommandType type) {

        this.type = type;

    }

    public Command(CommandType type, String argument) {

        this.type = type;
        this.argument = argument;

    }

    public CommandType getType() {

        return type;

    }


    public void setType(CommandType type) {

        this.type = type;

    }

    public String getArguments() {

        return argument;

    }

    public void setArguments(String argument) {

        this.argument = argument;

    }

}
