package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

/**
 * Created by Alex on 05/01/2016.
 * Command. Subtracts numbers.
 */
public class Sub extends Command {
    @Override
    public String description() {
        return "Subtract two numbers. (optional) Puts answer in variable";
    }

    @Override
    public String use() {
        return "sub <num1> <num2> (the same as <num1> - <num2>) write result in console \n sub <varName> <num> <num> ... <num>";
    }

    @Override
    public boolean execute(String[] args) { //Add var num ... num OR Add num .. num
        args = Mem.getValuesInArgs(args);
        if (ArgManipulation.isNumber(args[0])) { //option 2
            int ans = Integer.valueOf(args[0]);
                for (int i = 1; i < args.length; i++) {
                    if (!ArgManipulation.isNumber(args[i])){
                        Viewer.print("an argument wasn't a number: " + args[i]);
                        return false;
                    }
                    ans -= Integer.valueOf(args[i]);
                }
                Viewer.print(String.valueOf(ans));
                return true;
        } else {
                int ans = Integer.valueOf(args[1]);
                for (int i = 2; i < args.length; i++) {
                    if (!ArgManipulation.isNumber(args[i])){
                        Viewer.print("an argument wasn't a number: " + args[i]);
                        return false;
                    }
                    ans -= Integer.valueOf(args[i]);
                }
                Lookup.getMemInstance().add(new MemPair(args[0], String.valueOf(ans)));
                return true;
        }
    }
}