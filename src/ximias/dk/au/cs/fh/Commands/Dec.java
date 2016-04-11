package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.MemPair;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 05/04/2016.
 * Command. Decrement a variable
 */
public class Dec extends Command {
    @Override
    public String use() {
        return "dec <varName> (optional) <amount>";
    }

    @Override
    public String description() {
        return "Decrement a variable";
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        String[] a = {"*"+args[0]};
        a=Mem.getValuesInArgs(a);
        if (ArgManipulation.isNumber(a[0])) {
            if (args.length == 1) {
                Mem.add(new MemPair(args[0], String.valueOf(Integer.valueOf(a[0]) - 1)));
                return true;
            } else if (ArgManipulation.isNumber(args[1])){
                Mem.add(new MemPair(args[0],String.valueOf(Integer.valueOf(a[0])-Integer.valueOf(args[1]))));
                return true;
            }
        }
        Viewer.print("The variable you are trying to Decrement was not a number:" + a[0]);
        return false;
    }
}
