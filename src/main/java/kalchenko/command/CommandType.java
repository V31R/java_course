package kalchenko.command;

public enum CommandType {

    ADD,
    PRINT,
    TOGGLE,
    QUIT;

    public static CommandType getType(String string){


        CommandType result = null;

        for(CommandType commandType: CommandType.values()){

            if(commandType.equals(string)){

                result = commandType;

            }

        }

        return result;

    }

    public boolean equals(String string){

        return this.name().equals(string);

    }

}

