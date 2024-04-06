package connect4withmachine;

public class Player {
    private String name;
    private long bestTime;
    private char token;
    private boolean isHisTurn;

    public Player(String name, char token) {
        this.name = name;
        this.token = token;
        this.bestTime = 0;
    }

    public String getName() {
        return name;
    }

    public long getBestTime() {
        return bestTime;
    }
    
    public void setBestTime(long bestTime) {
        this.bestTime = bestTime;
    }

    public char getToken() {
        return token;
    }

    public boolean isHisTurn() {
        return isHisTurn;
    }

    public void setHisTurn(boolean hisTurn) {
        isHisTurn = hisTurn;
    }

    /**
     * Play
     * @param board
     * @return
     */
    public int play(Board board) {
        String col;

        System.out.println("Enter a column");
        col = Utilities.readString();

        if (col.equals("x") || col.equals("X")) {
            System.out.println("Game exited");
            return -1;
        }

        while (!board.putToken(getToken(), Integer.parseInt(col) - 1)) {
            System.out.println("Column is full, please choose another one");
            col = Utilities.readString();
        }

        return Integer.parseInt(col);
    }

}
