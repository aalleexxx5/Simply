package ximias.dk.au.cs.fh.Components;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    public ArrayList<String> readFileList(String filename){ // utility function for reading files
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            ArrayList<String> out = new ArrayList<String>();
            line = reader.readLine();
            while(line != null) {
                out.add(line);
                line = reader.readLine();
            }
            reader.close();
            return out;
        } catch (FileNotFoundException e) {
            System.out.println("The file "+ filename +" was not found!");
            return null;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public String readFile(String filename){ // utility function for reading files
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

    public boolean writeFile(String output, String filename){ //utility function for writing files
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            writer.write(output);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}