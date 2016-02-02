package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

/**
 * Created by Alex on 05/01/2016.
 */
public class Add extends Command{
    @Override
    public boolean execute(String[] args) { //Add var num ... num OR Add num .. num
        args = Mem.getValuesInArgs(args);
        if(Numbers.isNumber(args[0])){ //option 2
            int ans = 0;
            try {
                for (String num : args) {
                    ans += Integer.valueOf(num);
                }
                Viewer.print(String.valueOf(ans));
                return true;
            }catch (NumberFormatException e){
                return false;
            }
        }else{
            int ans = 0;
            try {
                for (int i = 1; i < args.length; i++) {
                    ans += Integer.valueOf(args[i]);
                }
                Lookup.getMemInstance().add(new MemPair(args[0],String.valueOf(ans)));
                return true;
            }catch(NumberFormatException e){
                return false;
            }
        }
    }
}
