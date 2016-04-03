package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.FileManager;
import ximias.dk.au.cs.fh.Components.Mem;

/**
 * Created by Alex on 02/04/2016.
 * Command. Appends to a file
 */
public class Log extends Command {
    @Override
    public String use() {
        return "log <src> <data>";
    }

    @Override
    public String description() {
        return "Adds a line to a file on disk";
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        String data = "\n"+ArgManipulation.argsToString(1, args.length, args);
        return args.length > 1 && FileManager.writeFile(data, args[0],true);
    }
}
