import java.util.HashMap;
public class CommandWords {
    private HashMap<String, CommandWord> validCommand;

    public CommandWords(){
        validCommand = new HashMap<String, CommandWord>();
        for(CommandWord command : CommandWord.values()){
            if(command != CommandWord.UNKNOWN){
                validCommand.put(command.toString(), command);
            }
        }
    }

    public CommandWord getCommandWord(String commandWord){
        CommandWord command = validCommand.get(commandWord);
        if(command != null){
            return command;
        }
        else{
            return CommandWord.UNKNOWN;
        }
    }

    public boolean isCommand(String aString){
        return validCommand.containsKey(aString);
    }

    public void showAll(){
        for(String command : validCommand.keySet()){
            System.out.print(command+" ");
        }
        System.out.println();
    }


}
