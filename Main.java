import java.io.*;
import java.util.*;

public class Main{

    public static void main(String[] args){
        Scanner read = new Scanner(System.in);

        String userInput = "";

        System.out.println();

        System.out.println("********************************");
        System.out.println("*                              *");
        System.out.println("*                              *");
        System.out.println("*                              *");
        System.out.println("* Welcome to Monopoly:COVID-19 *");
        System.out.println("*   By:Luna Liu & Paul Zhang   *");
        System.out.println("*                              *");
        System.out.println("*                              *");
        System.out.println("*                              *");
        System.out.println("********************************");
        System.out.println();

        int numPlayers = 0;
        while(true) {
            System.out.print("How many players are playing? (2-4) : ");
            userInput = read.nextLine();

            if(userInput.isEmpty()) {
                numPlayers = 2;
                System.out.println("The number of players is defaulted to " + numPlayers + ". ");
                break;

            }else {
                numPlayers = Integer.parseInt(userInput);
                if (numPlayers >= 2 && numPlayers <= 4) {
                    break;
                } else {
                    System.out.println("ERROR: must enter an integer between 2 to 4.");
                }
            }
        }

        System.out.println();

        List<Player> playersList = new ArrayList<Player>();
        String tokensList = "";

        for (int i = 1; i <= numPlayers; i++){
            String playerNickname = "";
            while(true) {
                System.out.print("Player " + i + " please enter a nickname (15 char max) : ");
                userInput = read.nextLine();

                if(userInput.isEmpty()) {
                    playerNickname = "Player" + i;
                    System.out.println("Player " + i + " your nickname is defaulted to " + playerNickname + ". ");
                    break;

                }else{
                    playerNickname = userInput;
                    if(playerNickname.length() <= 15) {
                        break;
                    }else{
                        System.out.println("ERROR: must enter a string length 0 to 15. ");
                    }
                }
            }


            String playerToken = "";
            while(true) {
                System.out.print("Player " + i + " please enter a token (1 char max) : ");
                userInput = read.nextLine();

                if(userInput.isEmpty()) {
                    playerToken = getRandomToken(tokensList);
                    System.out.println("Player " + i + " your token is defaulted to " + playerToken + ". ");
                    break;

                }else{
                    playerToken = userInput;
                    if(playerToken.length() <= 1) {
                        if(!tokensList.contains(playerToken)) {
                            break;
                        }else{
                            System.out.println("ERROR: token is already in use. ");
                        }
                    }else{
                        System.out.println("ERROR: must enter a char length 1. ");
                    }
                }
            }

            System.out.println();

            tokensList += playerToken;
            playersList.add(new Player(i-1, playerNickname, playerToken));
        }

        Board gameBoard = Board.getBoard(playersList);

        int cp = 0;
        while(playersList.size() > 1){
            gameBoard.print();

            Player currentPlayer =  playersList.get(cp);

            //is in jail?
            if(!currentPlayer.isInJail()) {
                System.out.print(currentPlayer.getNameCard() + " it's your turn, press ENTER to roll: ");
                read.nextLine();

                int roll = Dice.roll();
                System.out.println(currentPlayer.getNameCard() + " you rolled a " + roll);
                Block landedOn = gameBoard.movePlayerLocation(currentPlayer, roll);

                //is the block you landed on jail
                if(landedOn.isJail()) {
                    currentPlayer.goToJail();
                    System.out.println(currentPlayer.getNameCard() + " you landed in " + landedOn.getNameCard() + "!" );
                    printBlockInfo(landedOn);
                    System.out.print(currentPlayer.getNameCard() + " you must stay here for a turn, press ENTER to continue: ");
                    read.nextLine();

                }else if(landedOn.getOwner() == currentPlayer) {
                    System.out.print(currentPlayer.getNameCard() + " you landed on your own square, press ENTER to continue: ");
                    read.nextLine();

                }else if(landedOn.isStart()) {
                    System.out.print(currentPlayer.getNameCard() + " you landed on " + landedOn.getNameCard() + ", press ENTER to continue: ");
                    read.nextLine();

                }else{

                    //is the block available?
                    if(landedOn.isAvailable()) {

                        //does player have enough money?
                        if(currentPlayer.getBalance() >= landedOn.getCost()) {

                            //would player purchase?
                            System.out.print(currentPlayer.getNameCard() + " would you like to purchase " + landedOn.getNameCard() + " for " + landedOn.getCostCard() + "? (y/n) ");
                            String didPurchase = read.nextLine().toLowerCase();
                            if (didPurchase.isEmpty() || didPurchase.equals("y")) {
                                currentPlayer.purchaseBlock(landedOn);
                                printBlockInfo(landedOn);
                            }
                        }else{
                            System.out.print(currentPlayer.getNameCard() + " you cannot purchase " + landedOn.getNameCard() + ", press ENTER to continue: ");
                            read.nextLine();
                        }

                    }else{

                        //does player have enough money to pay?
                        if(currentPlayer.getBalance() >= landedOn.getCost()) {
                            System.out.print(currentPlayer.getNameCard() + " you must pay " + landedOn.getOwner().getNameCard() + " " + landedOn.getCostCard() +", press ENTER to pay: ");
                            read.nextLine();
                            currentPlayer.payAmount(landedOn.getCost(), landedOn.getOwner());
                        }else{
                            currentPlayer.payAmount(currentPlayer.getBalance(), landedOn.getOwner());
                            System.out.print(currentPlayer.getNameCard() + " you does not have enough money to pay " + landedOn.getOwner().getNameCard() + " you are out of the game, press ENTER to continue: ");
                            read.nextLine();

                            gameBoard.removePlayerFromBoard(currentPlayer);
                            playersList.remove(cp--);
                        }
                    }

                }

            }else{
                System.out.print(currentPlayer.getNameCard() + " press ENTER to go out of here! ");
                read.nextLine();
                currentPlayer.getOutOfJail();
                System.out.print(currentPlayer.getNameCard() + " you are now out, press ENTER continue: ");
                read.nextLine();
            }


            cp = ++cp % playersList.size();
            System.out.println();
        }

        System.out.println();
        System.out.println("***GAME OVER***");
        System.out.println("The winner is: " + playersList.get(0).getNameCard() + "!");
        System.out.println("Congratulations on surviving COVID-19.");
    }

    private static void printBlockInfo(Block b) {
        System.out.println();
        System.out.println("***TILE DESCRIPTION***");
        System.out.println(b.getNameCard());
        System.out.print("\"");
        for(int i=0; i<b.getInfo().length(); i++) {
            if(i!=0 && i%50==0) {
                System.out.print("\n");
            }

            System.out.print(b.getInfo().charAt(i));

        }
        System.out.println("\"");
        System.out.println();
    }

    private static String getRandomToken(String tokensList) {
        String token = "";

        while(true) {
            int r = (int) (Math.random() * 26 + 65);
            char c = (char)r;
            token = "" + c;

            if(!tokensList.contains(token)) {
                break;
            }
        }

        return token;
    }
}

