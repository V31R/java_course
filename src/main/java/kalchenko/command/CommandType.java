package kalchenko.command;

public enum CommandType {

    ADD,
    PRINT,
    SEARCH,
    TOGGLE,
    DELETE,
    EDIT,
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

}

