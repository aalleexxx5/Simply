package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.MemPair;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 05/04/2016.
 * Command. Adds local variables
 */
public class Local extends Command {
    @Override
    public String use() {
        return "local <varName> <value>";
    }

    @Override
    public String description() {
        return "set variables that can only be used in the same section. Local is stronger than set";
    }

    @Override
    public boolean execute(String[] args) {
        if (args.length<2) {
            Viewer.print("Not enough arguments");
            return false;
        }
        args=Mem.getValuesInArgs(args);
        String value = ArgManipulation.argsToString(1,args.length,args);
        Mem.addLocal(new MemPair(args[0],value));
        return true;
    }
}
