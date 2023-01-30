import java.util.zip.GZIPOutputStream;
import java.util.HashMap;
public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;



    Room riverBank;
    Room house;
    Room cemetery;
    Room barn;
    Room craftingTable;
    Room garden;
    public Game(){
        parser = new Parser();
        player = new Player();

    }
    public static void main(String args[]){
        Game game = new Game();
        game.createRooms();
        game.play();
    }

    private void createRooms(){
        riverBank = new Room("short river", "long river", "River Bank");
        garden = new Room("short garden","long garden", "garden");
        house = new Room("short house","long house", "house");
        cemetery = new Room("short cemetery","long cemetery", "cemetery");
        barn = new Room("short barn", "long barn", "barn");
        craftingTable = new Room("craft", "craft", "craftingTable");
        //River exits and invent
        riverBank.setExit("west", garden);

        //Garden exits and invent
        garden.setExit("east", riverBank);
        garden.setExit("north", house);
        garden.setExit("southwest",cemetery);
        garden.setExit("west", barn);

        //Item woods = new Item();
       // garden.setItem("woods", woods);

        //House exits and invent
        house.setExit("south", garden);
        house.setExit("craftingTable", craftingTable);

        Item backpack = new Item();
        house.setItem("backpack", backpack);

        //cemetery exits and invent
        cemetery.setExit("north", house);
        cemetery.setExit("northeast", garden);

        Item keys = new Item();
        cemetery.setItem("keys", keys);

        //Barn exits and invert
        barn.setExit("south", cemetery);
        barn.setExit("east", garden);

        Item rope = new Item();
        Item axe = new Item();
        barn.setItem("rope", rope);
        barn.setItem("axe", axe);

        //Staring parameters
        currentRoom = riverBank;
       // player.setItem("keys" ,keys);

        //CraftTable parameters
        craftingTable.setExit("craftTable", house);
        //Sub variables


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
                System.out.println("I don't know what you mean");
                helpHint();
                break;
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            case LOOK:
                look(command);
                break;
            case GRAB:
                grab(command);
                break;
            case DROP:
                drop(command);
                break;
            case INVENTORY:
                lookInventory(command);
                break;
            case CUT:
                cutTree(command);
            case TEST:
                testing(command);
                break;
            case OPEN:
                openCraft(command);
    }
        return wantToQuit;
}



    private void look(Command command){
        if(command.hasSecondWord()){
            System.out.println("You can't look at "+command.getSecondWord());
            helpHint();
            return;
        }
        System.out.println(currentRoom.getLongDescription());
        System.out.println(player.getItemString());

    }

    private void goRoom(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Go where?");
            helpHint();
            return;
        }
        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);
        if(nextRoom == null){
            System.out.println("There is no door");
        }else{
            if(nextRoom==house){
                if(!player.checkKeys()){
                    System.out.println("You don't have the keys, you should find them somewhere on the map");
                    helpHint();
                    return;
                }
            }

            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
        }
    }

    private boolean quit(Command command){
        if(command.hasSecondWord()){
            System.out.println("You can't quit "+ command.getCommandWord());
            helpHint();
            return false;
        }else{
            return true;
        }
    }

    private void grab(Command command){
        if (!command.hasSecondWord()){
            System.out.println("Grab what?");
            helpHint();
            return;
        }
        String wantedItem = command.getSecondWord();
        if(!player.checkBackpack()){
            if(player.invSize()>=1) {
                System.out.println("Your inventory is full right now, to keep more you need backpack, you will find it somewhere on the map ");
                helpHint();
                return;
            }
        }
        Item  item =  currentRoom.getItem(wantedItem);
        if(item==null){
            System.out.println("There is no item");
        }else{
            player.setItem(wantedItem,item);
            System.out.println("Now you will find "+wantedItem+" in your inventory");
        }
    }
    private void drop(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Drop what?");
            helpHint();
            return;
        }
        String wantedItemToDrop = command.getSecondWord();
        Item item = player.getItem(wantedItemToDrop);
        if(wantedItemToDrop==null){
            System.out.println("There is no item that you want to drop");
        }else{
            currentRoom.setItem(wantedItemToDrop,item);
            System.out.println("You dropped "+wantedItemToDrop+" from your inventory");
        }
    }

    private void lookInventory(Command command){
        if(command.hasSecondWord()){
            System.out.println("You can't look in inventory of "+command.getSecondWord());
            System.out.println("Your prompt request is: \"inventory\"");
            helpHint();
            return;
        }

        System.out.println(player.getItemString());
    }

    private void cutTree(Command command){
        if(currentRoom.getRoomName()=="Garden"){
            if (!command.hasSecondWord()){
                System.out.println("Cut what?");
                helpHint();
                return;
            }
            String wantedItem = command.getSecondWord();
            if(wantedItem.equals("tree") ){
                if(  player.checkAxe()){
                    //Item item = currentRoom.getItem("woods");
                    garden.setItem("wood", new Item());
                    System.out.println("You successfully cut the tree and now you will find woods on garden");
                    invHint();
                }else{
                    System.out.println("You do not have axe in your inventory, you will find it somewhere on the map");
                    invHint();
                }
            } else {
                System.out.println("You can not cut "+wantedItem);
            }
        }else {
            System.out.println("Here is nothing to cut");
        }
    }
    private void openCraft(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Open what?");
            helpHint();
            return;
        }
        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);
        if(nextRoom==null){
            System.out.println("Here is nothing to open");
        }else{
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
        }
    }

    //testing
    private void testing(Command command){
        //System.out.println(player.checkKeys());
        //System.out.println(player.invSize()>1);
       // System.out.println(player.checkBackpack());
        player.setItem("wood", new Item());
        player.setItem("backpack", new Item());
        player.setItem("key", new Item());
        player.setItem("rope", new Item());
        System.out.println(player.getItemString());
    }



    //
    private void printWelcome(){
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("You will find yourself near the bank of the river!");
        System.out.println("Your task is to escape to the opposite side");
        System.out.println("Type \"help\" if you need assistance");
        System.out.println();
        System.out.println("room description");
        System.out.println(currentRoom.getShortDescription());

    }
    private void invHint(){
        System.out.println("----NOTE----");
        System.out.println("You can always check your inventory using command: \"INVENTORY\"");
    }
    private void printHelp(){
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("You are in the "+currentRoom.getRoomName());
        System.out.println();
        System.out.println("Your command word are:");
        System.out.println("""
                 "go"\s
                 "quit"\s
                 "UNKNOWN"\s
                 "LOOK"\s
                 "GRAB"\s
                 "DROP"\s
                 "INVENTORY" \
                """);
    }
    private void helpHint(){
        System.out.println("----or----");
        System.out.println("Type: \"Help\" for more info");
    }
}
