import javax.crypto.Cipher;
import java.time.Instant;
import java.util.zip.GZIPOutputStream;
import java.util.HashMap;
public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;

    private Craft currentCraft;

    boolean wantToQuit;



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
        riverBank = new Room("As you step onto the dark riverbank, you feel a sense of foreboding wash over you. The dense trees and foliage overhead block out any light, and you sense that danger is lurking in the shadows.", "\n" +
                "The dark, foreboding riverbank stretches out before you, a path leading towards the west and into the heart of a terrifying forest. \nThe dense trees and foliage overhead block out any light, leaving the area shrouded in an eerie darkness. \nThe path towards the west looks narrow and treacherous, but you sense that this may be your only way out. \nThe fear of the unknown is palpable, but the thrill of adventure drives you forward as you set out along the path towards the ominous forest.", "River Bank");
        garden = new Room("As you step into the garden, you are surrounded by lush greenery and the sweet fragrance of blooming flowers. \nBut beyond the garden's walls, a dark and ominous forest looms, its shadows casting a foreboding air over the peaceful surroundings.","You find yourself in a garden surrounded by a dark and foreboding forest. \nThe lush greenery and blooming flowers are a stark contrast to the shadows \nthat seem to loom over everything beyond the garden's walls. \nTo the north, a path winds through the darkness, \nleading towards an abandoned house. To the west, another path leads to a barn, \nwhile to the southwest a barely visible path, littered with bones, beckons towards the cemetery. \nThe fear of the unknown is palpable, but the thrill of adventure drives you forward \nas you explore the paths that lead away from the safety of the garden and into the dark and mysterious heart of the forest.", "garden");
        house = new Room("As you approach the abandoned house, you can feel the dread growing within you. \nThe stillness of the air is broken only by the sound of creaking boards and the eerie aura of horror that seems to emanate from every corner of the dilapidated structure.","As you approach the abandoned house, you can feel the hairs on the back of your neck stand up. \nThe stillness of the air is broken only by the sound of creaking boards \nand the eerie aura of horror that seems to emanate from every corner of the dilapidated structure. \nIn the corner of the room, you spot a backpack and a workbench, \nboth of which are covered in a thick layer of dust, as if they have been untouched for a long time. \nThe fear of the unknown is palpable, but the thrill of adventure drives you forward as you explore the abandoned house, \nsearching for any clues or treasures that may be hidden within its walls.", "house");
        cemetery = new Room("As you enter the cemetery, you are surrounded by the stillness of death and the eerie aura of horror that seems to linger in the air. \nThe overgrown graves and towering headstones loom over you like silent sentinels, \nwatching your every move as you navigate the dark and foreboding paths.","As you enter the old cemetery, you are surrounded by the stillness of death and the eerie aura of horror that seems to linger in the air. \nThe overgrown graves and towering headstones loom over you like silent sentinels, watching your every move as you navigate the dark and foreboding paths. \nIn the center of the cemetery, you spot a large grave in the form of a statue of death, holding a scythe.\n A key hangs from the scythe, just within reach. To the north, another path beckons, leading towards the barn. \nThe fear of the unknown is palpable, but the thrill of adventure drives you forward as you explore the old cemetery, \nsearching for any clues or treasures that may be hidden among the graves.", "cemetery");
        barn = new Room("As you approach the abandoned barn, you are greeted by the sight of old, weathered wood and the musty smell of age. \nInside, you find a large collection of old items, from tools and machinery to piles of moldy hay and rusted implements. \nThe air is thick with the sense of neglect and forgotten time.", "As you approach the abandoned barn, you are surrounded by the eerie stillness of the surrounding forest. \nThe old, weathered wood of the barn creaks underfoot as you push open the door and step inside. \nThe musty smell of age fills your nostrils as you survey the cluttered interior, \nfilled with broken tools, rusted machinery, and piles of moldy hay. Your eyes are drawn to a glint of metal in the corner, \nwhere you find an ax and a rope lying amidst the debris. Two paths lead out of the barn, \none to the north towards the cemetery and the other to the west towards the dark garden. \nThe thrill of adventure calls to you as you weigh your options and prepare to continue your journey into the unknown.", "barn");
        craftingTable = new Room("craft", "As you approach the craftingTable, you feel a sense of urgency. \nYou know that you are in a dangerous place \nand that you need to find a way to escape the dark riverbank and avoid death. \nThe workbench is cluttered with tools and scraps of wood, \nbut you quickly spot what you need to craft something \nthat will help you cross the river to safety. With a renewed sense of purpose, you set to work, \ngathering materials and piecing together your creation. \nThe sound of wood being sawed and nails being driven fills the air as you work, and you feel a growing sense of hope \nthat you will soon be able to escape the dangers of the dark riverbank and find your way to safety.", "craftingTable");
        //River exits and invent
        riverBank.setExit("west", garden);
        riverBank.setExit("across", riverBank);

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


       // player.setItem("keys" ,keys);

        //CraftTable parameters
        craftingTable.setExit("craftingTable", house);
        //Sub variables
        currentCraft = new Craft();
        //Staring parameters
        currentRoom =riverBank;
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
        wantToQuit = false;

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
                break;
            case OPEN:
                openCraft(command);
                break;
            case CLOSE:
                closeCraft(command);
                break;
            case PUT:
                put(command);
                break;
            case TAKE:
                take(command);
                break;
            case CRAFT:
                craft();
                break;
            case TEST:
                testing(command);
                break;
            case TESTCRAFT:
                testCraft(command);
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
            if(direction.equals("across")){
                if(!(player.checkRaft())){
                    System.out.println("You do not have raft to cross the river");
                    return;
                }else {
                    if(player.getRaftCond()){
                        finishWin();
                    }else finishDie();

                }
            }else{
                currentRoom = nextRoom;
                System.out.println(currentRoom.getShortDescription());
            }


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
        if(item==null){
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
        if(currentRoom.getRoomName()=="garden"){
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

    // Commands for crafting "open" "close" "put" "take" "craft"

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

    private void closeCraft(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Close what?");
            helpHint();
            return;
        }
        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);
        if(nextRoom==null){
            System.out.println("Here is nothing to close");
        }else{
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
        }
    }

    private void put(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Drop what?");
            helpHint();
            return;
        }
        String wantedItemToPut = command.getSecondWord();
        Item item = player.getItem(wantedItemToPut);
        if(item==null){
            System.out.println("There is no item that you want to put");
        }else{
            currentCraft.setCraftItem(wantedItemToPut, item);
            currentCraft.setItemTime(wantedItemToPut);
            System.out.println(currentCraft.getItemString());
            System.out.println(currentCraft.getItemTime(wantedItemToPut));
        }
    }

    private void take(Command command){
        if (!command.hasSecondWord()){
            System.out.println("Grab what?");
            helpHint();
            return;
        }
        String wantedItem = command.getSecondWord();
        Item  item =  currentCraft.getCraftItem(wantedItem);
        if(item==null){
            System.out.println("There is no item");
        }else{
            player.setItem(wantedItem,item);
            System.out.println("Now you will find "+wantedItem+" in your inventory");
        }
    }

    private void craft(){
        //System.out.println(currentCraft.getItemString());
        if(!(currentCraft.checkItem("rope")&& currentCraft.checkItem("wood")&&currentCraft.checkAmount()==2)){
            System.out.println("There is not enough items to craft \n You can find needed items somewhere on the map \n Or you put unnecessary item");
            helpHint();
            return;
        }
        if(currentCraft.getItemTime("rope").isAfter(currentCraft.getItemTime("wood"))){
            player.setItem("raft", new Item());
            player.setRaftCond(true);
        }else {
            player.setItem("raft", new Item());
            player.setRaftCond(false);
        }
        System.out.println("Congratulations, now the raft for crossing the river is ready!");

    }
    private void testCraft(Command command){
       // System.out.println(player.getRaftCond());
        finishWin();
        finishDie();
    }

    //testing

    private void testing(Command command){
        //System.out.println(player.checkKeys());
        //System.out.println(player.invSize()>1);
       // System.out.println(player.checkBackpack());
        player.setItem("wood", new Item());
        player.setItem("backpack", new Item());
        player.setItem("keys", new Item());
        player.setItem("rope", new Item());
        player.setItem("axe", new Item());
        System.out.println(player.getItemString());
    }



    //
    private void printWelcome(){
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("As you stand on the dark riverbank, you realize that you have found yourself in a perilous situation. \nYou sense that danger is imminent, and you must act quickly to escape.\nYour goal is to use your skills and cunning to navigate the treacherous waters and find a way out of this dark and ominous place. \nEvery step you take must be calculated, as any misstep could lead to your downfall. You must keep your wits about you and be ready for anything \nas you make your way along the riverbank, searching for a way to escape the impending danger that looms all around you. \nThe prize for survival is freedom, and you are determined to claim it no matter what it takes.");
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

    private void finishWin(){
        System.out.println("|----------------------------|");
        System.out.println("|                            |");
        System.out.println("|                            |");
        System.out.println("|                            |");
        System.out.println("|         W   I   N          |");
        System.out.println("|                            |");
        System.out.println("|                            |");
        System.out.println("|                            |");
        System.out.println("|----------------------------|");


        wantToQuit = true;
    }
    private void  finishDie(){
        System.out.println("|----------------------------|");
        System.out.println("|                            |");
        System.out.println("|                            |");
        System.out.println("|                            |");
        System.out.println("|         D   I   E          |");
        System.out.println("|                            |");
        System.out.println("|                            |");
        System.out.println("|                            |");
        System.out.println("|----------------------------|");
        wantToQuit = true;
    }

}
