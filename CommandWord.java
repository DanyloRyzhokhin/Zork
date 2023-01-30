public enum CommandWord {

    GO("go"), QUIT("quit"), HELP("help"),
    UNKNOWN("?"), LOOK("look"), GRAB("grab"), DROP("drop"),
    INVENTORY("inventory"), TEST("test"), CUT("cut"), OPEN("open");

    private String commandString;
    CommandWord(String commandString){
            this.commandString = commandString;
    }

    public String toString(){
        return commandString;
    }
}
