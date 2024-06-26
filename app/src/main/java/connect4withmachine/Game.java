/**
 * @Author: Ricardo Uriegas
 * @Date: 09/04/2024
 * @Description: This class represents the game
 * @Version: 1
 */
package connect4withmachine;

// gson import
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class Game {
    private Player player1;
    private Player player2;
    private Board board;
    private Chronometer chronometer = new Chronometer();

    /**
     * Constructor
     * 
     * @param player1
     * @param player2
     * @param board
     * @param chronometer
     */
    public Game(Player player1, Player player2, Board board, Chronometer chronometer) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.chronometer = chronometer;
    }

    /**
     * Play the game
     * 
     * @return void
     */
    public void play() {
        chronometer.start();
        do {
            // check if someone won
            if (theresAWinner(board, player1, player2))
                return;

            // check draw
            if (board.checkLastRow()) {
                board.printBoard();
                System.out.println("It's a draw");
                chronometer.stop();
                deleteGame("game.json");

                // enter to continue
                System.out.println("Press enter to continue");
                Utilities.readString();
                return;
            }

            board.printBoard();
            player1.setHisTurn(true);
            player2.setHisTurn(false);

            if (player1.play(board) == -1) {
                saveGame("game.json");
                Utilities.exit();
            }

            // check if someone won
            if (theresAWinner(board, player1, player2))
                return;

            // check draw
            if (board.checkLastRow()) {
                board.printBoard();
                System.out.println("It's a draw");
                chronometer.stop();
                deleteGame("game.json");

                // enter to continue
                System.out.println("Press enter to continue");
                Utilities.readString();
                return;
            }

            board.printBoard();
            player1.setHisTurn(false);
            player2.setHisTurn(true);

            if (player2.play(board) == -1) {
                saveGame("game.json");
                Utilities.exit();
            }
        } while (true);
    }

    /**
     * Function to check if there's a winner (player1 or player2)
     * 
     * @param board
     * @param player1
     * @param player2
     * @return boolean (true if there's a winner, false otherwise)
     */
    public boolean theresAWinner(Board board, Player player1, Player player2) {
        if (board.isWinner(player1.getToken())) {
            board.printBoard();
            System.out.println(player1.getName() + " wins");
            chronometer.stop();
            player1.setTime(chronometer);
            System.out.println("Best time: " + player1.getTime().getElapsedTime());
            deleteGame("game.json");

            // rank list
            if (player2 instanceof PC) {
                RankList rankList = new RankList();
                rankList.addPlayerMachine(player1);
                rankList.saveRanking();
            } else {
                RankList rankList = new RankList();
                rankList.addPlayer(player1);
                rankList.saveRanking();
            }

            // enter to continue
            System.out.println("Press enter to continue");
            Utilities.readString();

            return true;
        }

        if (board.isWinner(player2.getToken())) {
            board.printBoard();
            System.out.println(player2.getName() + " wins");
            chronometer.stop();
            // parse int
            player2.setTime(chronometer);
            System.out.println("Best time: " + player2.getTime().getElapsedTime());
            deleteGame("game.json");

            // rank list
            if (!(player2 instanceof PC)) {
                // if the player 2 is a human, then we add it to the rank list
                RankList rankList = new RankList();
                rankList.addPlayer(player2);
                rankList.saveRanking();
            }
            // enter to continue
            System.out.println("Press enter to continue");
            Utilities.readString();

            return true;
        }

        return false;
    }

    /**
     * Save the game to a file using Gson (the file name is game.json)
     * TODO: Use Utilities.writeFile instead of FileWriter
     * 
     * @param filename
     * @return void
     */
    public void saveGame(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject gameJson = new JsonObject();

            // Serialize player1
            gameJson.add("player1", gson.toJsonTree(player1));

            // Serialize player2
            if (player2 instanceof PC) {
                // If player2 is an instance of PCPlayer, create a new PCPlayer object
                PC pcPlayer = new PC("PC", 'O');
                gameJson.add("player2", gson.toJsonTree(pcPlayer));
            } else {
                // Serialize player2 normally
                gameJson.add("player2", gson.toJsonTree(player2));
            }

            // Serialize board and chronometer
            chronometer.stop();
            gameJson.add("board", gson.toJsonTree(board));
            gameJson.addProperty("chronometerRunning", chronometer.isRunning());
            gameJson.addProperty("startTime", chronometer.getStartTime());

            gson.toJson(gameJson, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the game from the file game.json
     * TODO: Use Utilities.readFile instead of FileReader
     * 
     * @param filename
     * @return Game (Player1, Player2, Board and Chronometer)
     */
    public static Game loadGame(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            @SuppressWarnings("deprecation")
            JsonParser parser = new JsonParser();
            @SuppressWarnings("deprecation")
            JsonObject gameJson = parser.parse(reader).getAsJsonObject();

            // Deserialize player1
            Player player1 = gson.fromJson(gameJson.get("player1"), Player.class);

            // Deserialize player2
            Player player2;
            if (gameJson.get("player2").getAsJsonObject().get("name").getAsString().equals("PC")) {
                // If player2 is PCPlayer, deserialize as PCPlayer
                player2 = gson.fromJson(gameJson.get("player2"), PC.class);
            } else {
                // Deserialize player2
                player2 = gson.fromJson(gameJson.get("player2"), Player.class);
            }

            // Deserialize board and chronometer
            Board board = gson.fromJson(gameJson.get("board"), Board.class);
            Chronometer chronometer = new Chronometer();
            chronometer.setStartTime(gameJson.get("startTime").getAsLong());
            chronometer.start();

            // if (gameJson.get("chronometerRunning").getAsBoolean()) {

            // }

            return new Game(player1, player2, board, chronometer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to delete a file
     * Use
     * 
     * @param filename
     * @return void
     */
    public static void deleteGame(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Function to check if a game is saved (it just checks if the file exists)
     * 
     * @param filename
     * @return boolean (true if the file exists, false otherwise)
     */
    public static boolean isGameSaved(String filename) {
        File file = new File(filename);
        return file.exists() && !file.isDirectory();
    }

}
