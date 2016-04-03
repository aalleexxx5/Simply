package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

/**
 * Created by Alex on 02/04/2016.
 * Command. Loads data frm a file on disk
 */
public class Load extends Command {
    @Override
    public String use() {
        return "load <src> (optional)<varName>";
    }

    @Override
    public String description() {
        return "Load data from a file, write date either in console or variable";
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        if (args.length==1){
            Viewer.print(FileManager.readFile(args[0]));
            return true;
        }else if (args.length>1){
            Lookup.getMemInstance().add(new MemPair(args[1],FileManager.readFile(args[0])));
            return true;
        }
        Viewer.print("No arguments were given");
        return false;
    }
}
