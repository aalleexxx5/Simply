package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Constants;
import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.MemPair;

/**
 * Created by Alex on 05/01/2016.
 */
public class Set extends Command {
    @Override
    public boolean execute(String[] args) {
        if(args.length<2){
            return false;
        }
        args = Mem.getValuesInArgs(args);
        for(int i = 2; i<args.length;i++){
            if(args[i].equals(Constants.SPACE_SYMBOL)){
                args[1]+=" ";
            }else{
                args[1]+=args[i];
            }
        }
        Lookup.getMemInstance().add(new MemPair(args[0], args[1]));
        return true;
    }
}
