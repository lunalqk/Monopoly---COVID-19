import java.util.List;

public class Board {
    private static Board mBoard;

    private Block[] board;
    private Player[][] playerLocation;

    private Board (List<Player> playerList){
        board = new Block[16];
        for(int i=0; i<board.length; i++) {
            board[i] = new Block(Info.name[i], Info.desc[i], Info.cost[i]);
        }

        playerLocation = new Player[16][4];
        for(Player p : playerList) {
            playerLocation[0][p.getNumber()] = p;
        }
    }

    public static Board getBoard(List<Player> playerList){
        if(mBoard == null) {
            mBoard = new Board(playerList);
        }
        return mBoard;
    }

    public void print(){
        System.out.println("|0"+getO(0)+"|  |1 "+getO(1)+"|  |2 "+getO(2)+"|  |3 "+getO(3)+"|  |4 "+getO(4)+"|");
        System.out.println("|  "+getP(0,0)+getP(0,1)+"|  |  "+getP(1,0)+getP(1,1)+"|  |  "+getP(2,0)+getP(2,1)+"|  |  "+getP(3,0)+getP(3,1)+"|  |  "+getP(4,0)+getP(4,1)+"|");
        System.out.println("|  "+getP(0,2)+getP(0,3)+"|  |  "+getP(1,2)+getP(1,3)+"|  |  "+getP(2,2)+getP(2,3)+"|  |  "+getP(3,2)+getP(3,3)+"|  |  "+getP(4,2)+getP(4,3)+"|");

        System.out.println("|15"+getO(15)+"|                                |5 "+getO(5)+"|");
        System.out.println("|  "+getP(15,0)+getP(15,1)+"|                                |  "+getP(5,0)+getP(5,1)+"|");
        System.out.println("|  "+getP(15,2)+getP(15,3)+"|                                |  "+getP(5,2)+getP(5,3)+"|");

        System.out.println("|14"+getO(14)+"|                                |6 "+getO(6)+"|");
        System.out.println("|  "+getP(14,0)+getP(14,1)+"|                                |  "+getP(6,0)+getP(6,1)+"|");
        System.out.println("|  "+getP(14,2)+getP(14,3)+"|                                |  "+getP(6,2)+getP(6,3)+"|");

        System.out.println("|13"+getO(13)+"|                                |7 "+getO(7)+"|");
        System.out.println("|  "+getP(13,0)+getP(13,1)+"|                                |  "+getP(7,0)+getP(7,1)+"|");
        System.out.println("|  "+getP(13,2)+getP(13,3)+"|                                |  "+getP(7,2)+getP(7,3)+"|");

        System.out.println("|12"+getO(12)+"|  |11"+getO(11)+"|  |10"+getO(10)+"|  |9 "+getO(9)+"|  |8 "+getO(8)+"|");
        System.out.println("|  "+getP(12,0)+getP(12,1)+"|  |  "+getP(11,0)+getP(11,1)+"|  |  "+getP(10,0)+getP(10,1)+"|  |  "+getP(9,0)+getP(9,1)+"|  |  "+getP(8,0)+getP(8,1)+"|");
        System.out.println("|  "+getP(12,2)+getP(12,3)+"|  |  "+getP(11,2)+getP(11,3)+"|  |  "+getP(10,2)+getP(10,3)+"|  |  "+getP(9,2)+getP(9,3)+"|  |  "+getP(8,2)+getP(8,3)+"|");

        System.out.println("------------------------------------------------");

        System.out.println("|" + getT(0) + "|" + getT(1) + "|" + getT(2) + "|" + getT(3) + "|  ");
        System.out.println("|" + getN(0) + "|" + getN(1) + "|" + getN(2) + "|" + getN(3) + "|  ");
        System.out.println("|" + getC(0) + "|" + getC(1) + "|" + getC(2) + "|" + getC(3) + "|  ");
        System.out.println("|" + getB(0) + "|" + getB(1) + "|" + getB(2) + "|" + getB(3) + "|  ");
    }




    // returns the block player landed on
    public Block movePlayerLocation(Player p, int numberOfMoves) {
        int currSquare = removePlayerFromBoard(p);
        int nextSquare = (currSquare + numberOfMoves) % 16;
        if(currSquare >= 10 && currSquare <= 15 && nextSquare >= 0 && nextSquare <= 5) {
            System.out.println(p.getNameCard() + " you passed [START], here's [$200]. ");
            p.addAmount(200);
        }
        playerLocation[nextSquare][p.getNumber()] = p;

        return board[nextSquare];


    }


    //find player and remove him from board
    //returns the square number
    public int removePlayerFromBoard(Player p) {
        for(int i=0; i<playerLocation.length; i++) {
            for(int j=0; j<playerLocation[0].length; j++) {
                if(playerLocation[i][j] == p) {
                    playerLocation[i][j] = null;
                    return i;
                }
            }
        }
        return -1;
    }

    private int isPlayerOnBoard(int playerNumber) {
        for(int i=0; i<playerLocation.length; i++) {
            if(playerLocation[i][playerNumber] != null) {
                return i;
            }
        }
        return -1;
    }

    private String getO(int squareNumber) {
        if(board[squareNumber].isStart()) {
            return "START";
        }else if(board[squareNumber].isJail()) {
            return "JAIL";
        }else{
            if(board[squareNumber].isAvailable()) {
                return "    ";
            }else{
                String s = board[squareNumber].getOwner().getToken();
                return s + s + s + s;
            }
        }
    }

    private String getP(int squareNumber, int playerNumber) {
        if(playerLocation[squareNumber][playerNumber] == null) {
            return "  ";
        }else{
            return playerLocation[squareNumber][playerNumber].getToken() + " ";
        }
    }


    private String getT(int playerNumber) {
        int squareNumber = isPlayerOnBoard(playerNumber);
        if(squareNumber >=0 ) {
            Player p = playerLocation[squareNumber][playerNumber];

            return "     " + p.getToken() + "     ";
        }else{
            return "           ";
        }
    }


    private String getN(int playerNumber) {
        int squareNumber = isPlayerOnBoard(playerNumber);
        if(squareNumber >=0 ) {
            Player p = playerLocation[squareNumber][playerNumber];
            String name = "  ";
            for(int i=0; i<7; i++) {
                if(i<p.getName().length()) {
                    name = name + p.getName().replaceAll("\\s+","").charAt(i);
                }else{
                    name = name + " ";
                }
            }

            return name + "  ";
        }else{
            return "           ";
        }
    }

    private String getC(int playerNumber) {
        int squareNumber = isPlayerOnBoard(playerNumber);
        if(squareNumber >=0 ) {
            Player p = playerLocation[squareNumber][playerNumber];
            String balance = "" + p.getBalance();
            String name = " $$: ";
            for(int i=0; i<6; i++) {
                if(i<balance.length()) {
                    name = name + balance.charAt(i);
                }else{
                    name = name + " ";
                }
            }

            return name;
        }else{
            return "           ";
        }
    }


    private String getB(int playerNumber) {
        int squareNumber = isPlayerOnBoard(playerNumber);
        if(squareNumber >=0 ) {
            Player p = playerLocation[squareNumber][playerNumber];
            String numProp = "" + p.getBlocksList().size();
            String name = " #P: ";
            for(int i=0; i<6; i++) {
                if(i<numProp.length()) {
                    name = name + numProp.charAt(i);
                }else{
                    name = name + " ";
                }
            }

            return name;
        }else{
            return "           ";
        }
    }


}
