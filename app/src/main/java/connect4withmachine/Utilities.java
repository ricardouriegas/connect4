package connect4withmachine;

// gson import
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;
import java.io.*;

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

    public static int compareTime (Chronometer time1, Chronometer time2) {
        if (time1.getStartTime() < time2.getStartTime()) {
            return -1;
        } else if (time1.getStartTime() > time2.getStartTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    public static String readFile (String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                sb.append((char) i);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void writeFile (String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
