public class Block {
    private String name;
    private String info;
    private int cost;
    private boolean isJail;
    private Player owner;


    public Block (String a, String b, int c){
        name = a.toUpperCase();
        info = b;
        cost = c;
        if(!name.equals("START") && cost == 0) {
            isJail = true;
        }else{
            isJail = false;
        }
        owner = null;
    }


    //getters
    public String getName() {
        return name;
    }
    public String getInfo(){
        return info;
    }
    public int getCost(){
        return cost;
    }
    public boolean isJail() {
        return isJail;
    }
    public boolean isStart() {
        return name.equals("START");
    }
    public boolean isAvailable (){
        return owner==null;
    }
    public Player getOwner() {
        return owner;
    }


    public void setOwner (Player p){
        owner = p;
    }


    public String getNameCard() {
        return "[" + name + "]";
    }

    public String getCostCard() {
        return "[$" + cost + "]";
    }
}
