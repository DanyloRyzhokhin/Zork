import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
public class Room {
    private String description;
    private HashMap<String,Room>exits;
    private String longDescription;
    public Room(String description, String longDescription){
        this.description = description;
        exits = new HashMap<String,Room>();
        this.longDescription = longDescription;
    }

    private String getExitString(){
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit: keys){
            returnString += " "+exit;
        }
        return returnString;
    }
    public String getLongDescription(){
        return longDescription +"\n" + getExitString();
    }

    public void setExit(String direction, Room neighbor){
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction){
        return exits.get(direction);
    }

    public String getShortDescription(){
        return description;
    }

}