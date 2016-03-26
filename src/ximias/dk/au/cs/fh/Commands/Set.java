package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

/**
 * Created by Alex on 05/01/2016.
 * Command. Sets a variable to a value.
 */
public class Set extends Command {
    @Override
    public String description() {
        return "sets a variable to a given value";
    }

    @Override
    public String use(){
        return "set <name> <value>";
    }

    @Override
    public boolean execute(String[] args) {
        if(args.length<2){
            Viewer.print("no variable name and/or value was given");
            return false;
        }
        args = Mem.getValuesInArgs(args);
        String value = ArgManipulation.argsToString(1,args.length,args);
        Lookup.getMemInstance().add(new MemPair(args[0], value));
        return true;
    }
}
