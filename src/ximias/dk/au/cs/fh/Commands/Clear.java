package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 24/03/2016.
 */
public class Clear extends Command {
    @Override
    public boolean execute(String[] args) {
        Viewer.clear();
        return true;
    }
}
