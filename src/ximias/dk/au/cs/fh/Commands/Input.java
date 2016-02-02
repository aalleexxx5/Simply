package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 11/01/2016.
 */
public class Input extends Command {
    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        return Lookup.runMainCommand("set", new String[]{args[0], Viewer.getInput()});
    }
}
