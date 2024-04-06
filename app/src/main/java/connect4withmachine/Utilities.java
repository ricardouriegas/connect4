package connect4withmachine;

import java.util.*;

public class Utilities {
    private static Scanner in = new Scanner(System.in);

    public static int readInt () {
        // here should be an infinite try catch until the user put a correct int
        do {
            try {
                return Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        } while (true);
    }

    public static String readString () {
        do {
            try {
                return in.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter a valid string");
            }
        } while (true);
    }

    public static void sleep (int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
