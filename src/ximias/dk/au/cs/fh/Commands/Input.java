package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 11/01/2016.
 * Command. Waits for input from user.
 */
public class Input extends Command {
    @Override
    public String description() {
        return "sets a variable to a user input";
    }

    @Override
    public String use(){
        return "input <variable>";
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        return Lookup.runMainCommand("set", new String[]{args[0], Viewer.getInput()});
    }
}
