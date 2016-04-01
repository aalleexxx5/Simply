package ximias.dk.au.cs.fh.Components;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class FileManager {

    public static ArrayList<String> readFileList(String filename){ // utility function for reading files
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

// --Commented out by Inspection START (25/03/2016 20:11):
//    public String readFile(String filename){ // utility function for reading files
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(filename));
//            String line;
//            String out = "";
//            line = reader.readLine();
//            while(line != null) {
//                out = out + line + "\n";
//                line = reader.readLine();
//            }
//            reader.close();
//            return out;
//        } catch (FileNotFoundException e) {
//            System.out.println("The file "+ filename +" was not found!");
//            return "";
//        } catch (IOException e){
//            e.printStackTrace();
//            return "";
//        }
//    }
// --Commented out by Inspection STOP (25/03/2016 20:11)

// --Commented out by Inspection START (25/03/2016 20:11):
//    public boolean writeFile(String output, String filename){ //utility function for writing files
//        try {
//            PrintWriter writer = new PrintWriter(new FileWriter(filename));
//            writer.write(output);
//            writer.close();
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
// --Commented out by Inspection STOP (25/03/2016 20:11)
}