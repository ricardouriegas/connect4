package connect4withmachine;

// gson import
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class RankList {
    // 2 ordered lists, one for the Player vs Player and the other one for the
    // Player vs Machine
    private ArrayList<Player> playerList = new ArrayList<>();
    private ArrayList<Player> playerMachineList = new ArrayList<>();

    public RankList() {
        // recover the ranking from the file
        recoverRanking();
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public ArrayList<Player> getPlayerMachineList() {
        return playerMachineList;
    }

    public void addPlayer(Player player) {        
        // inster by order
        if (playerList.isEmpty()) {
            playerList.add(player);
        } else {
            // insert into the list by order
            for (int i = 0; i < playerList.size(); i++) {
                if (compareTime(player.getTime(), playerList.get(i).getTime()) == -1) {
                    playerList.add(i, player);
                    return;
                }
            }
            playerList.add(player);
        }
    }

    public void addPlayerMachine(Player player) {
        // order the list by the the best time
        if (playerMachineList.isEmpty()) {
            playerMachineList.add(player);
        } else {
            // insert into the list by order
            for (int i = 0; i < playerMachineList.size(); i++) {
                if (compareTime(player.getTime(), playerMachineList.get(i).getTime()) == -1) {
                    playerMachineList.add(i, player);
                    return;
                }
            }
            playerMachineList.add(player);
        }
    }

    /**
     * This function compares the time of two players,
     * it returns -1 if the time of the first player is less than the time of the
     * second player
     * it returns 1 if the time of the first player is greater than the time of the
     * second player
     * it returns 0 if the time of the first player is equal to the time of the
     * second player
     * 
     * @param time1
     * @param time2
     * @return int
     */
    public int compareTime(Chronometer time1, Chronometer time2) {
        long minutes1 = time1.getMinutes();
        long seconds1 = time1.getSeconds();
        long microSeconds1 = time1.getMicroSeconds();

        long minutes2 = time2.getMinutes();
        long seconds2 = time2.getSeconds();
        long microSeconds2 = time2.getMicroSeconds();

        if (minutes1 < minutes2) {
            return -1;
        } else if (minutes1 > minutes2) {
            return 1;
        } else {
            if (seconds1 < seconds2) {
                return -1;
            } else if (seconds1 > seconds2) {
                return 1;
            } else {
                if (microSeconds1 < microSeconds2) {
                    return -1;
                } else if (microSeconds1 > microSeconds2) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    /**
     * This function recovers the ranking from the file
     * 
     * @return void
     */
    public void recoverRanking() {
        try {
            // recover the ranking from the file
            Gson gson = new Gson();
            @SuppressWarnings("deprecation")
            JsonParser parser = new JsonParser();
            @SuppressWarnings("deprecation")
            JsonObject rankingJson = parser.parse(Utilities.readFile("ranking.json")).getAsJsonObject();

            // Deserialize playerList
            Player[] playerList = gson.fromJson(rankingJson.get("playerList"), Player[].class);
            for (Player player : playerList) {
                this.playerList.add(player);
            }

            // Deserialize playerMachineList
            Player[] playerMachineList = gson.fromJson(rankingJson.get("playerMachineList"), Player[].class);
            for (Player player : playerMachineList) {
                this.playerMachineList.add(player);
            }

        } catch (Exception e) {
            // if the file does not exist, create it
            Utilities.writeFile("ranking.json", "");
        }
    }

    /**
     * This function saves the ranking in the file
     * 
     * @return void
     */
    public void saveRanking() {
        try {
            // save the ranking in the file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject rankingJson = new JsonObject();

            // Serialize playerList
            rankingJson.add("playerList", gson.toJsonTree(playerList));

            // Serialize playerMachineList
            rankingJson.add("playerMachineList", gson.toJsonTree(playerMachineList));

            Utilities.writeFile("ranking.json", gson.toJson(rankingJson));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("===Player vs Player===\n");
        for (int i = 0; i < playerList.size(); i++) {
            sb.append(i + 1).append(". Name: ").append(playerList.get(i).getName()).append(" \n\tTime:")
                    .append(playerList.get(i).getTime().getElapsedTime()).append("\n");
        }

        sb.append("===Player vs Machine===\n");
        for (int i = 0; i < playerMachineList.size(); i++) {
            sb.append(i + 1).append(". Name: ").append(playerMachineList.get(i).getName()).append(" \n\tTime:")
                    .append(playerMachineList.get(i).getTime().getElapsedTime()).append("\n");
        }
        sb.append("\n");

        return sb.toString();
    }

}
