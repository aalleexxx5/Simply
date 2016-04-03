package ximias.dk.au.cs.fh.Components;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    static ArrayList<String> readFileList(String filename){ // utility function for reading files
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            ArrayList<String> out = new ArrayList<>();
            line = reader.readLine();
            while(line != null) {
                out.add(line);
                line = reader.readLine();
            }
            reader.close();
            return out;
        } catch (FileNotFoundException e) {
            System.out.println("The file "+ filename +" was not found!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                System.exit(0);
            }
            System.exit(0);
            return null;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }


    public static String readFile(String filename){ // utility function for reading files
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            String out = "";
            line = reader.readLine();
            while(line != null) {
                out = out + line + "\n";
                line = reader.readLine();
            }
            reader.close();
            return out;
        } catch (FileNotFoundException e) {
            System.out.println("The file "+ filename +" was not found!");
            return "";
        } catch (IOException e){
            e.printStackTrace();
            return "";
        }
    }

    public static boolean writeFile(String output, String filename, boolean append){ //utility function for writing files
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename,append));
            writer.write(output);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}