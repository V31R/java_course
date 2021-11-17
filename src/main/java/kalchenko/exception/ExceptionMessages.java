package kalchenko.exception;

import kalchenko.command.CommandType;

import java.util.Locale;

public class ExceptionMessages {

    public static String incorrectCommand(){

        return "Incorrect command was entered.";

    }

    public static String incorrectArgument(CommandType commandType){

        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("Incorrect argument for ")
                .append(commandType.name().toLowerCase(Locale.ROOT))
                .append(".").toString();

    }

    public static String incorrectIndex(CommandType commandType){

        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("Incorrect index for ")
                .append(commandType.name().toLowerCase(Locale.ROOT))
                .append(".").toString();

    }

}
