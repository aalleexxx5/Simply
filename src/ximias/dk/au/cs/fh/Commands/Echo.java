package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Constants;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 05/01/2016.
 * Command. Prints the arguments to screen.
 */
public class Echo extends Command {
    @Override
    public String description() {
        return "Writes arguments to console. '_' concatenates two arguments with no space in between";
    }

    @Override
    public String use(){
        return "echo <Word> (optional) <Word> ... <Word>\n To write '*' use "+Constants.ESCAPE_STAR;
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        String print = ArgManipulation.argsToString(0,args.length,args);
        Viewer.print(print);
        return true;
    }
}