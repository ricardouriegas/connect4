package connect4withmachine;

public class PC extends Player {
    public PC(String name, char token) {
        super(name, token);
    }

    public int play(Board board) {
        int bestScore = Integer.MIN_VALUE;
        int bestCol = 0;
        for (int i = 0; i < board.getCols(); i++) {
            if (board.isColumnFull(i)) {
                continue;
            }

            board.putToken(getToken(), i);
            int score = minmax(board, 5, true); // 6 is great but it takes a lot of processing
            board.removeToken(i);

            if (score > bestScore) {
                bestScore = score;
                bestCol = i;
            }
        }

        board.putToken(getToken(), bestCol);
        return bestCol;
    }

    // minmax algorithm
    public int minmax(Board board, int depth, boolean isMaximizing) {
        if (depth == 0 || board.isFull()) {
            return board.evaluateBoard(board, getToken());
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.getCols(); i++) {
                if (board.isColumnFull(i)) {
                    continue;
                }

                board.putToken(getToken(), i);
                int score = minmax(board, depth - 1, false);
                if (score > bestScore) {
                    bestScore = score;
                }
                board.removeToken(i);
            }
            return bestScore; // Return the evaluation score in maximizing part
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.getCols(); i++) {
                if (board.isColumnFull(i)) {
                    continue;
                }


                char opponentToken = getToken() == 'X' ? 'O' : 'X';
                board.putToken(opponentToken, i);
                int score = minmax(board, depth - 1, true);
                bestScore = Math.min(score, bestScore);
                board.removeToken(i);
            }
            return bestScore; // Return the evaluation score in minimizing part
        }
    }

}
