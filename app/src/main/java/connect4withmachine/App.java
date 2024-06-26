/*
 * @Author: Ricardo Uriegas
 * @Date: 09/04/2024
 * @Description: Main class to run the program
 * @Version: 1
 */
package connect4withmachine;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    /**
     * Run the program
     * @return void
     */
    void run() {
        // check if there is a saved game
        if (Game.isGameSaved("game.json")) {
            System.out.println("Theres is a saved game, do you want to load it? (y/n)");
            String opc = Utilities.readString();
            if (opc.equals("y") || opc.equals("Y")) {
                Game loadedGame = Game.loadGame("game.json");
                loadedGame.play();
            } else {
                Game.deleteGame("game.json");
            }
        }

        //  

        // connect 4
        int opc;
        do {
            menu();
            opc = Utilities.readInt();
            switch (opc) {
                case 1:
                    // player vs player
                    PlayerVsPlayer();
                    break;
                case 2:
                    // player vs pc
                    PlayerVsPC();
                    break;
                case 3:
                    // ranking
                    printRanking();
                    break;
            }
        } while (opc != 0);
    }

    /**
     * Menu
     */
    void menu() {
        System.out.println("WELCOME TO CONNECT 4");
        System.out.println("Choose an option");
        System.out.println("1) Player vs Player");
        System.out.println("2) Player vs PC");
        System.out.println("3) Ranking");
        System.out.println("0) Exit");
    }

    /**
     * Function to print the ranking
     * @return void
     */
    void printRanking() {
        RankList rankList = new RankList();
        // clear screen
        System.out.println("\033[H\033[2J");
        System.out.println("=====RANKING=====");
        if (rankList.getPlayerList().size() > 0 || rankList.getPlayerMachineList().size() > 0) {
            int opc;
            do {
                System.out.println("Choose an option");
                System.out.println("1) Player vs Player");
                System.out.println("2) Player vs Machine");
                System.out.println("0) Exit");
                opc = Utilities.readInt();

                switch (opc) {
                    case 1:
                        rankList.printPlayerList();
                        System.out.println("Press enter to continue");
                        Utilities.readString();
                        break;
                    case 2:
                        rankList.printPlayerMachineList();
                        System.out.println("Press enter to continue");
                        Utilities.readString();
                        break;
                }

                System.out.println("\033[H\033[2J");
            } while (opc != 0);

        } else {
            System.out.println("No ranking yet");
        }
    }

    /**
     ************************************************************
     * Player Vs Player
     ************************************************************
     */
    void PlayerVsPlayer() {
        // Player vs Player
        Chronometer chronometer = new Chronometer();

        System.out.println("Enter player 1 name");
        String name1 = Utilities.readString();
        if (name1.equals("PC") || name1.equals("pc")){
            System.out.println("That's an invalid name");
            return;
        }
    
        System.out.println("Enter player 2 name");
        String name2 = Utilities.readString();
        if (name2.equals("PC") || name2.equals("pc")){
            System.out.println("That's an invalid name");
            return;
        }

        Player player1 = new Player(name1, 'X');
        Player player2 = new Player(name2, 'O');

        Board board = new Board();
        Game game = new Game(player1, player2, board, chronometer);

        game.play();

    }

    /**
     ************************************************************
     * Player Vs PC
     ************************************************************
     */
    void PlayerVsPC() {
        // Player vs PC
        Chronometer chronometer = new Chronometer();

        System.out.println("Enter player name");
        String name = Utilities.readString();

        Player player = new Player(name, 'X');
        PC pc = new PC("PC", 'O');

        Board board = new Board();
        Game game = new Game(player, pc, board, chronometer);

        game.play();
    }
}
