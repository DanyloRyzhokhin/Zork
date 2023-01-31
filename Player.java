import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

public class Player {
    private HashMap<String, Item> inventory;
    private boolean raftCondition;
    Player(){
        inventory = new HashMap<>();
    }
    public String getItemString(){
        String returnString = "PLayer items:";
        Set<String> keys =  inventory.keySet();
        for(String item: keys){
            returnString +=" "+ item;
        }
        return returnString;
    }
    public void setItem(String name,Item item ){
        inventory.put(name,item);
    }
    public Item getItem(String name){
        return inventory.remove(name);
    }

    public boolean checkKeys(){
        boolean isKeys = false;
        Set<String> keys =inventory.keySet();
        /*for(String item: keys){
            if(item.)
        }*/
        if(keys.contains("keys")){
            isKeys = true;
        }
        return isKeys;
    }
    public boolean checkAxe(){
        boolean isAxe = false;
        Set<String> keys = inventory.keySet();
        if(keys.contains("axe"))
            isAxe  = true;
        return isAxe;
    }
    public boolean checkBackpack(){
        boolean isBack = false;
        Set<String> keys = inventory.keySet();
        if(keys.contains("backpack")) isBack = true;
        return isBack;
    }

    public int invSize(){
        Set<String> keys = inventory.keySet();
        return keys.size();
    }

    public void setRaftCond(Boolean raftCondition){
        this.raftCondition = raftCondition;
    }
    public boolean getRaftCond(){
        return raftCondition;
    }

    public boolean checkRaft(){
        boolean isBack = false;
        Set<String> keys = inventory.keySet();
        if(keys.contains("raft")) isBack = true;
        return isBack;
    }
}
