package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Constants;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 05/01/2016.
 * Command. Prints the arguments to screen
 */
public class Echo extends Command {
    @Override
    public String description() {
        return "Writes arguments to console. '_' concatenates two arguments with no space in between";
    }

    @Override
    public String use(){
        return "echo <arg1> (optional) <arg2> ... <argn>\n To write '*' use "+Constants.ESCAPE_STAR;
    }

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