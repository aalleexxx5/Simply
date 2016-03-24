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
        String value = "";
        for(int i = 1; i<args.length;i++){
            if(i+1>=args.length||args[i+1].contains(Constants.NO_SPACE_SYMBOL)){
                value+=args[i];
                i++;
            }else{
                value+=args[i]+" ";
            }
        }
        Lookup.getMemInstance().add(new MemPair(args[0], value));
        return true;
    }
}
