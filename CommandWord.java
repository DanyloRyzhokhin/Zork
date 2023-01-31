public enum CommandWord {

    GO("go"), QUIT("quit"), HELP("help"),
    UNKNOWN("?"), LOOK("look"), GRAB("grab"), DROP("drop"),
    INVENTORY("inventory"), TEST("test"), CUT("cut"),

    //crafting commands:
    OPEN("open"), PUT("put"), CLOSE("close"), TAKE("take"), CRAFT("craft"),
    TESTCRAFT("tc")
    ;

    private String commandString;
    CommandWord(String commandString){
            this.commandString = commandString;
    }

    public String toString(){
        return commandString;
    }
}
