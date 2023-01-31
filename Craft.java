import java.time.Instant;
import java.util.HashMap;
import java.util.Set;


public class Craft {

    private HashMap<String, Item> itemsToCraft;


    Craft(){

        itemsToCraft = new HashMap<>();
    }

    public void setCraftItem(String name, Item item){
       itemsToCraft.put(name, item);
       //itemsToCraft.get(name).time = Instant.now();
    }
    public Item getCraftItem (String name){
        itemsToCraft.get(name).time = null;
        return itemsToCraft.remove(name);
    }
    public void setItemTime(String name){
        itemsToCraft.get(name).time = Instant.now();
    }
    public Instant getItemTime(String name){
        return itemsToCraft.get(name).time;
    }

    public void look(String name){
        System.out.println(itemsToCraft);
    }

    public boolean checkItem(String name){
        Set<String> keys =  itemsToCraft.keySet();
        return keys.contains(name);
    }

    public int checkAmount(){
        Set<String> keys =  itemsToCraft.keySet();
        return keys.size();
    }

    public String getItemString(){
        String returnString = "Crafting table items:";
        Set<String> keys =  itemsToCraft.keySet();
        for(String item: keys){
            returnString +=" "+ item;
        }
        return returnString;
    }
}
