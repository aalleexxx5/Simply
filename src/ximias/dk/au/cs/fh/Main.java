package ximias.dk.au.cs.fh;

import ximias.dk.au.cs.fh.Commands.Jumpto;
import ximias.dk.au.cs.fh.Components.FileManager;
import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.Viewer;

import javax.management.openmbean.ArrayType;
import javax.swing.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Viewer(args).setVisible(true);
            }
        });
    }
}