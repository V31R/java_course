package kalchenko.command;

import java.util.Locale;

public enum CommandType {

    ADD{

        public boolean commandArgumentVerification(String[] arguments){

            return arguments.length > 1;//command name and task name

        }

    },
    PRINT{

        public boolean commandArgumentVerification(String[] arguments){

            return arguments.length == 1 || (arguments[1].equals("all"));//only command name or command name and parameter "all"

        }

    },
    SEARCH{

        public boolean commandArgumentVerification(String[] arguments){

            return arguments.length == 2;//command name and substring

        }

    },
    TOGGLE{

        public boolean commandArgumentVerification(String[] arguments){

            if(arguments.length == 2) {//command name and index

                try {

                    Integer index = Integer.valueOf(arguments[1]);
                    return true;

                } catch (NumberFormatException e) {

                    return  false;

                }

            }

            return false;

        }

    },
    DELETE{

        public boolean commandArgumentVerification(String[] arguments){

            if(arguments.length == 2) {//command name and index

                try {

                    Integer index = Integer.valueOf(arguments[1]);
                    return true;

                } catch (NumberFormatException e) {

                    return  false;

                }

            }

            return false;

        }

    },
    EDIT{

        public boolean commandArgumentVerification(String[] arguments){

            if(arguments.length == 3) {//command name, index and new name

                try {

                    Integer index = Integer.valueOf(arguments[1]);
                    return true;

                } catch (NumberFormatException e) {

                    return  false;

                }

            }

            return false;

        }

    },
    QUIT{

        public boolean commandArgumentVerification(String[] arguments){

            return arguments.length == 1;//only command name

        }

    };

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

    public abstract boolean commandArgumentVerification(String[] arguments);

}

