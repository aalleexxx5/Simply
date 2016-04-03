package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.FileManager;
import ximias.dk.au.cs.fh.Components.Mem;

/**
 * Created by Alex on 02/04/2016.
 * Command. saves a variable to a file
 */
public class Save extends Command {
    @Override
    public String use() {
        return "save <src> <data>";
    }

    @Override
    public String description() {
        return "saves a variable to a file. Overwrites the file. For adding a line, see log.";
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        String data = ArgManipulation.argsToString(1, args.length, args);
        return args.length > 1 && FileManager.writeFile(data, args[0],false);
    }
}
