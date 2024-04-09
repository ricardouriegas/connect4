package connect4withmachine;

public class Player {
    private String name;
    private Chronometer time;
    private char token;
    private boolean isHisTurn;

    /**
     * Constructor
     * @param name
     * @param token
     */
    public Player(String name, char token) {
        this.name = name;
        this.token = token;
        this.time = null;
    }

    /**
     * Getters and setters
     */
    public String getName() {
        return name;
    }

    public Chronometer getTime() {
        return time;
    }
    
    public void setTime(Chronometer time) {
        this.time = time;
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
     * Function to realize a move
     * @param board
     * @return
     */
    public int play(Board board) {
        String col;

        System.out.println("Enter a column");
        col = Utilities.readString();

        if (col.equals("x") || col.equals("X")) {
            System.out.println("Game exited (CAUTION: If you start another game, the previous one will be lost)");
            return -1;
        }

        while (!board.putToken(getToken(), Utilities.stringToInt(col) - 1)) {
            col = Utilities.readString();
        }

        return Integer.parseInt(col);
    }

}
