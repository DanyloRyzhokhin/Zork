public class Game {
    private Room currentRoom;
    public Game(){}
    public static void main(String args[]){
        Game game = new Game();
        game.createRooms();
        game.play();
    }

    private void createRooms(){
        Room riverBank = new Room("");
        Room garden = new Room("");
        Room house = new Room("");
        Room cemitary = new Room("");
    }

    public void play(){
        printWelcome();
        boolean finished = false;
        while(!finished){
        }
        System.out.println("You finished the game");
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
