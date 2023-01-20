public class Game {
    private Room currentRoom;
    private Parser parser;
    public Game(){
        parser = new Parser();
    }
    public static void main(String args[]){
        Game game = new Game();
        game.createRooms();
        game.play();
    }

    private void createRooms(){
        Room riverBank = new Room("short", "long");
        Room garden = new Room("short","long");
        Room house = new Room("short","long");
        Room semitary = new Room("short","long");

       riverBank.setExit("west", garden);

       currentRoom = garden;

    }

    public void play(){
        printWelcome();
        boolean finished = false;
        while(!finished){
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("You finished the game");
    }

    private boolean processCommand(Command command){
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord){
            case UNKNOWN:
                break;
            case HELP:
                break;
            case GO:
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            case LOOK:
                look(command);
                break;
        }
        return wantToQuit;
    }
    private void look(Command command){
        if(command.hasSecondWord()){
            System.out.println("You can't look at "+command.getSecondWord());
            return;
        }

        System.out.println(currentRoom.getLongDescription());
    }

    private void goRoom(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);
        if(nextRoom == null){
            System.out.println("There is no door");
        }else{
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
        }
    }

    private boolean quit(Command command){
        if(command.hasSecondWord()){
            System.out.println("You can't quit "+ command.getCommandWord());
            return false;
        }else{
            return true;
        }
    }


    private void printWelcome(){
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("You will find yourself near the bank of the river!");
        System.out.println("Your task is to escape to the opposite side");
        System.out.println("Type \"help\" if you need assistance");
        System.out.println();
        System.out.println("room description");


    }
}
