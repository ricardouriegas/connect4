package connect4withmachine;

public class Game {
    private Player player1;
    private Player player2;
    private Board board;
    private Chronometer chronometer = new Chronometer();
    

    public Game(Player player1, Player player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        play();
    }

    public void play() {
        // chronometer.start();
        // TODO: detect if there a saved game and ask the user if he wants to continue
        do {
            // check if someone won
            if (theresAWinner(board, player1, player2))
                return;

            board.printBoard();
            player1.setHisTurn(true);
            player2.setHisTurn(false);

            if (player1.play(board) == -1) {
                // TODO: save game
                return;
            }

            // check if someone won
            if (theresAWinner(board, player1, player2))
                return;

            board.printBoard();
            player1.setHisTurn(false);
            player2.setHisTurn(true);

            if (player2.play(board) == -1) {
                // TODO: save game
                return;
            }
        } while (true);
    }

    public boolean theresAWinner(Board board, Player player1, Player player2) {
        if (board.isWinner(player1.getToken())) {
            board.printBoard();
            System.out.println(player1.getName() + " wins");
            // chronometer.stop();
            // player1.setBestTime(chronometer.getTime());
            return true;
        }

        if (board.isWinner(player2.getToken())) {
            board.printBoard();
            System.out.println(player2.getName() + " wins");
            // player2.setBestTime(chronometer.getTime());
            return true;
        }

        return false;
    }

    public void saveGame() {

    }
}
