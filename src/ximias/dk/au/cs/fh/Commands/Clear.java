package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 24/03/2016.
 * Command to clear screen
 */
public class Clear extends Command {
    @Override
    public String use() {
        return name();
    }

    @Override
    public String description() {
        return "Clears console";
    }

    @Override
    public boolean execute(String[] args) {
        Viewer.clear();
        return true;
    }
}
