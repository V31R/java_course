package kalchenko.command;

public class Command {

    private CommandType type;
    private String[] argument;

    public Command() {}

    public Command(CommandType type) {

        this.type = type;

    }

    public Command(CommandType type, String[] argument) {

        this.type = type;
        this.argument = argument;

    }

    public CommandType getType() {

        return type;

    }


    public void setType(CommandType type) {

        this.type = type;

    }

    public String[] getArguments(boolean flag) {

        return argument;

    }

    public String getArguments() {

        return argument[0];

    }

    public void setArguments(String argument) {

        this.argument= new String[1];
        this.argument[0] = argument;

    }
    public void setArguments(String[] argument, boolean flag) {

        this.argument = argument;

    }

}
