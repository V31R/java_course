package kalchenko.command;

public enum CommandType {

    ADD,
    PRINT,
    TOGGLE,
    QUIT;

    public static CommandType getType(String string){

        CommandType result = null;

        try{

            result=CommandType.valueOf(string);

        }
        catch(IllegalArgumentException illegalArgumentException){

            return null;

        }
        catch(NullPointerException nullPointerException){

            return null;

        }

        return result;

    }

    public boolean equals(String string){

        return this.name().equals(string);

    }

}

