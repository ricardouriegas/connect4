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
    

    public Game(Player player1, Player player2, Board board, Chronometer chronometer) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.chronometer = chronometer;
        // play();
    }

    public void play() {
        chronometer.start();
        do {
            // check if someone won
            if (theresAWinner(board, player1, player2))
                return;

            board.printBoard();
            player1.setHisTurn(true);
            player2.setHisTurn(false);

            if (player1.play(board) == -1) {
                // TODO: save game
                saveGame("game.json");
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
                saveGame("game.json");
                return;
            }
        } while (true);
    }

    public boolean theresAWinner(Board board, Player player1, Player player2) {
        if (board.isWinner(player1.getToken())) {
            board.printBoard();
            System.out.println(player1.getName() + " wins");
            chronometer.stop();
            player1.setBestTime(chronometer.getElapsedTime());
            System.out.println("Best time: " + player1.getBestTime() + " s");
            deleteGame("game.json");
            return true;
        }

        if (board.isWinner(player2.getToken())) {
            board.printBoard();
            System.out.println(player2.getName() + " wins");
            chronometer.stop();
            player2.setBestTime(chronometer.getElapsedTime());
            System.out.println("Best time: " + player2.getBestTime() + " s");
            deleteGame("game.json");
            return true;
        }

        return false;
    }

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
            gameJson.add("board", gson.toJsonTree(board));
            gameJson.addProperty("chronometerStartTime", chronometer.getStartTime());
            gameJson.addProperty("chronometerRunning", chronometer.isRunning());
    
            gson.toJson(gameJson, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Game loadGame(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject gameJson = parser.parse(reader).getAsJsonObject();
    
            // Deserialize player1
            Player player1 = gson.fromJson(gameJson.get("player1"), Player.class);
    
            // Deserialize player2
            Player player2;
            if (gameJson.get("player2").getAsJsonObject().get("name").getAsString().equals("PC")) {
                // If player2 is PCPlayer, deserialize as PCPlayer
                player2 = gson.fromJson(gameJson.get("player2"), PC.class);
            } else {
                // Deserialize player2 normally
                player2 = gson.fromJson(gameJson.get("player2"), Player.class);
            }
    
            // Deserialize board and chronometer
            Board board = gson.fromJson(gameJson.get("board"), Board.class);
            Chronometer chronometer = new Chronometer();
            chronometer.setStartTime(gameJson.get("chronometerStartTime").getAsLong());
            if (gameJson.get("chronometerRunning").getAsBoolean()) {
                chronometer.start();
            }
    
            return new Game(player1, player2, board, chronometer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    

    public static void deleteGame(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    public static boolean isGameSaved(String filename) {
        File file = new File(filename);
        return file.exists() && !file.isDirectory();
    }
}
