package kalchenko.commandStrategy;

import kalchenko.command.Command;
import kalchenko.exception.ExceptionMessages;

import kalchenko.task.TaskList;

public class ConcreteStrategyEdit implements  CommandStrategy{

    @Override
    public void execute(TaskList taskList, Command command) {

        String[] args = command.getArguments().split(" ");

        Integer index = Integer.valueOf(args[0]);

        if(!taskList.getTasks().containsKey(index)){

            throw new IllegalArgumentException(ExceptionMessages.incorrectIndex(command.getType()));

        }


        StringBuilder description= new StringBuilder();
        for(int i = 1; i < args.length; i++){

            description.append(args[i]).append(" ");

        }

        taskList.edit(index,description.toString().trim());

    }

}
