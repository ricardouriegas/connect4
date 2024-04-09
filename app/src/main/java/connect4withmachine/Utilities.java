package connect4withmachine;

import java.util.*;
import java.io.*;

public class Utilities {
    private static Scanner in = new Scanner(System.in);

    /**
     * Function that serves to read a valid int
     * 
     * @return int
     */
    public static int readInt() {
        // here should be an infinite try catch until the user put a correct int
        do {
            try {
                return Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        } while (true);
    }

    /**
     * This function serves to read a valid string
     * 
     * @return String
     */
    public static String readString() {
        do {
            try {
                return in.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter a valid string");
            }
        } while (true);
    }

    /**
     * The purpose of the function is sleep the program certain time
     * 
     * @param time
     * @return void
     */
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function is to compare the the time of 2 chronometers
     * 
     * @param time1
     * @param time2
     * @return int
     */
    public static int compareTime(Chronometer time1, Chronometer time2) {
        if (time1.getStartTime() < time2.getStartTime()) {
            return -1;
        } else if (time1.getStartTime() > time2.getStartTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Function to read a file
     * 
     * @param fileName
     * @return String
     */
    public static String readFile(String fileName) {
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

    /**
     * Function to create a file or overwrite a file
     * 
     * @param fileName
     * @param content
     * @return void
     */
    public static void writeFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to delete a file
     * @param fileName
     * @return void
     */
    public static void deleteFile (String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Function to transform a string into a int
     */
    public static int stringToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Function to exit the program
     * @return void
     */
    public static void exit() {
        System.exit(0);
    }
}
