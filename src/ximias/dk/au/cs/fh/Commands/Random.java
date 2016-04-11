package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

/**
 * Created by Alex on 01/04/2016.
 * Command. Gives a random number between two values.
 */
public class Random extends Command{
    @Override
    public String use() {
        return "random <VarName> <startNumber> <endNumber>";
    }

    @Override
    public String description() {
        return "Gives a random number between (and including) two values";
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        if (ArgManipulation.isNumber(args[1])||ArgManipulation.isNumber(args[2])){
            int value = (int)Math.floor(Math.random()*Integer.valueOf(args[2])+1)+Integer.valueOf(args[1]);
            Lookup.getMemInstance().add(new MemPair(args[0], String.valueOf(value)));
            return true;
        }
        Viewer.print("One of the arguments: "+args[1] +" and "+args[2]+" isn't a number");
        return false;
    }
}
