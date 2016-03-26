package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

/**
 * Created by Alex on 05/01/2016.
 * Command. Subtracts numbers.
 */
public class Sub extends Command{
    @Override
    public String description() {
        return "Subtract two numbers. (optional) Puts answer in variable <arg1>";
    }

    @Override
    public String use(){
        return "Subtract <arg2> from <arg1>, write result in console \n Subtract <arg3> from <arg2>, result in <arg1>";
    }
    @Override
    public boolean execute(String[] args) { //Add var num ... num OR Add num .. num
        args = Mem.getValuesInArgs(args);
        if(ArgManipulation.isNumber(args[0])){ //option 2
            int ans = Integer.valueOf(args[0]);
            try {
                for (int i = 1; i < args.length; i++) {
                    ans -= Integer.valueOf(args[i]);
                }
                Viewer.print(String.valueOf(ans));
                return true;
            }catch (NumberFormatException e){
                return false;
            }
        }else{
            int ans = Integer.valueOf(args[1]);
            try {
                for (int i = 2; i < args.length; i++) {
                    ans -= Integer.valueOf(args[i]);
                }
                Lookup.getMemInstance().add(new MemPair(args[0],String.valueOf(ans)));
                return true;
            }catch(NumberFormatException e){
                return false;
            }
        }
    }
}