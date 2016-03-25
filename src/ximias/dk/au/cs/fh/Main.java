package ximias.dk.au.cs.fh;

import ximias.dk.au.cs.fh.Components.Viewer;

import javax.swing.*;

class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Viewer(args).setVisible(true));
    }
}