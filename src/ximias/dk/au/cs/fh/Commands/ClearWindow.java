package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 06/09/2016.
 */
public class ClearWindow extends Command {
    @Override
    public String use() {
        return "clearwindow";
    }

    @Override
    public String description() {
        return "clears the window of all components. Takes no arguments.";
    }

    @Override
    public boolean execute(String[] args) throws InterruptedException {
        Viewer.removeAllElements();
        return true;
    }
}
