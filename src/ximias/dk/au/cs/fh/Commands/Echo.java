package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Constants;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 05/01/2016.
 */
public class Echo extends Command {
    @Override
    public boolean execute(String[] args) {
        String print = "";
        args = Mem.getValuesInArgs(args);
        for (int i=0; i<args.length;i++){
            args[i] = args[i].replace(Constants.ESCAPE_STAR,"*");
            if (i+1>=args.length||args[i+1].contains(Constants.NO_SPACE_SYMBOL)){
                print = print + args[i];
                i++;
            }else{
                print =print + args[i] + " ";
            }
        }
        Viewer.print(print);
        return true;
    }
}