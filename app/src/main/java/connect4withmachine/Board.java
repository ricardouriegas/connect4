package connect4withmachine;

public class Board {
    private char[][] board;
    private int rows;
    private int cols;

    public Board() {
        this.rows = 6;
        this.cols = 7;
        this.board = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public void printBoard() {
        // clear screen
        System.out.print("\033[H\033[2J");
        // System.out.println("Time: " + chronometer.getTime() + " ms");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("|" + board[i][j]);
            }
            System.out.println("|");
        }
        System.out.println(" 1 2 3 4 5 6 7");
    }

    public boolean isFull() {
        for (int i = 0; i < cols; i++) {
            if (board[0][i] == ' ') {
                return false;
            }
        }
        return true;
    }

    private boolean checkWin(char[][] board, int row, int col, char token, int dr, int dc, int count) {
        if (count == 4) {
            return true;
        }
    
        int nextRow = row + dr;
        int nextCol = col + dc;
    
        if (isValidCell(nextRow, nextCol) && board[nextRow][nextCol] == token) {
            return checkWin(board, nextRow, nextCol, token, dr, dc, count + 1);
        }
        
        return false;
    }
    
    
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
    
    

    public boolean isWinner(char token) {
        int[][] directions = { { 1, 0 }, { 0, 1 }, { 1, 1 }, { -1, 1 } }; 

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == token) {
                    for (int[] dir : directions) {
                        int dr = dir[0];
                        int dc = dir[1];
                        if (checkWin(board, i, j, token, dr, dc, 1)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isColumnFull(int col) {
        return board[0][col] != ' ';
    }

    public boolean putToken(char token, int col) {
        if (col < 0 || col >= cols) {
            System.out.println("Choose a correct colummn");
            return false;
        }

        if (isColumnFull(col)) {
            return false;
        }

        for (int i = rows - 1; i >= 0; i--) {
            if (board[i][col] == ' ') {
                board[i][col] = token;
                return true;
            }
        }

        return false;
    }

    public void removeToken(int col) {
        for (int i = 0; i < rows; i++) {
            if (board[i][col] != ' ') {
                board[i][col] = ' ';
                break;
            }
        }
    }

    public char getToken(int row, int col) {
        return board[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void setBoard(char token, int row, int col) {
        this.board[row][col] = token;
    }

    /**
     * Methods
     */
    public int evaluateBoard(Board board, char playerToken) {
        char opponentToken = (playerToken == 'X') ? 'O' : 'X';
        int playerScore = 0;
        int opponentScore = 0;

        // Evaluar horizontalmente
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col <= board.getCols() - 4; col++) {
                playerScore += evaluateLine(board, row, col, 0, 1, playerToken);
                opponentScore += evaluateLine(board, row, col, 0, 1, opponentToken);
            }
        }

        // Evaluar verticalmente
        for (int col = 0; col < board.getCols(); col++) {
            for (int row = 0; row <= board.getRows() - 4; row++) {
                playerScore += evaluateLine(board, row, col, 1, 0, playerToken);
                opponentScore += evaluateLine(board, row, col, 1, 0, opponentToken);
            }
        }

        // Evaluar diagonales ascendentes (\)
        for (int row = 0; row <= board.getRows() - 4; row++) {
            for (int col = 0; col <= board.getCols() - 4; col++) {
                playerScore += evaluateLine(board, row, col, 1, 1, playerToken);
                opponentScore += evaluateLine(board, row, col, 1, 1, opponentToken);
            }
        }

        // Evaluar diagonales descendentes (/)
        for (int row = 3; row < board.getRows(); row++) {
            for (int col = 0; col <= board.getCols() - 4; col++) {
                playerScore += evaluateLine(board, row, col, -1, 1, playerToken);
                opponentScore += evaluateLine(board, row, col, -1, 1, opponentToken);
            }
        }

        return playerScore - opponentScore;
    }

    private int evaluateLine(Board board, int startRow, int startCol, int rowDir, int colDir, char playerToken) {
        int score = 0;
        int countPlayer = 0;
        int countEmpty = 0;

        for (int i = 0; i < 4; i++) {
            char token = board.getToken(startRow + i * rowDir, startCol + i * colDir);
            if (token == playerToken) {
                countPlayer++;
            } else if (token == ' ') {
                countEmpty++;
            }
        }

        if (countPlayer == 4) {
            score += 10000;
        } else if (countPlayer == 3 && countEmpty == 1) {
            score += 100;
        } else if (countPlayer == 2 && countEmpty == 2) {
            score += 10;
        }

        return score;
    }

}
