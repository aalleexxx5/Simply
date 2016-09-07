package ximias.dk.au.cs.fh;

import ximias.dk.au.cs.fh.Components.Viewer;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (args.length<1){
            System.out.println("Please input the file to run.");
            System.out.println("App created by Ximias");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                args = new String[] {br.readLine()};
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        final String[] finalArgs = args;
        SwingUtilities.invokeLater(() -> new Viewer(finalArgs).setVisible(true));
    }
}