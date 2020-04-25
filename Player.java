import java.util.ArrayList;
import java.util.List;

public class Player {
    private int balance;
    private int number;
    private String name;
    private String token;
    private boolean isInJail;
    private List<Block> blocksList;

    public Player(int playerNumber, String playerName, String playerToken){
        balance = 1000;
        number = playerNumber;
        name = playerName;
        token = playerToken;
        isInJail = false;
        blocksList = new ArrayList<Block>();
    }

    //getters
    public int getBalance(){
        return balance;
    }
    public int getNumber(){
        return number;
    }
    public String getName() {
        return name;
    }
    public String getToken(){
        return token;
    }
    public boolean isInJail(){
        return isInJail;
    }
    public List<Block> getBlocksList() {
        return blocksList;
    }

    //increase balance by amount
    public void addAmount(int amount){
        balance += amount;
    }

    //decrease balance by amount
    private void subAmount(int amount) {
        balance -= amount;
    }

    //returns true if able to pay
    //false otherwise, pay all they have and is dead
    public boolean payAmount(int amount, Player p) {
        if(balance >= amount) {
            balance -= amount;
            p.addAmount(amount);
            return true;
        }else{
            p.addAmount(balance);
            return false;
        }
    }

    //returns true if able to buy the property
    //false otherwise, ie not enough money
    public boolean purchaseBlock (Block a) {
        int blockCost = a.getCost();
        if (balance >= blockCost) {
            subAmount(blockCost);
            blocksList.add(a);
            a.setOwner(this);
            return true;
        } else {
            return false;
        }
    }

    public void goToJail(){
        isInJail = true;
    }

    public void getOutOfJail(){
        isInJail = false;
    }

    public String getNameCard() {
        return "[" + name.toUpperCase() + "]";
    }

}
