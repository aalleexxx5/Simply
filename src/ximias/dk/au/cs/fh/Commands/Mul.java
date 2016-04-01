package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

/**
 * Created by Alex on 05/01/2016.
 * Command. Multiplies two numbers
 */
public class Mul extends Command {
    @Override
    public String description() {
        return "Multiplies two numbers. (optional) Puts answer in variable <arg1>";
    }

    @Override
    public String use() {
        return "Multiply <arg1> by <arg2>, write result in console \n Multiply <arg2> by <arg3>, result in <arg1>";
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        if (ArgManipulation.isNumber(args[0])) {
            int ans = Integer.valueOf(args[0]);
            if (args.length > 1) {
                for (int i = 1; i < args.length; i++) {
                    if (!ArgManipulation.isNumber(args[i])){
                        Viewer.print("an argument wasn't a number: " + args[i]);
                        return false;
                    }
                    ans *= Integer.valueOf(args[i]);
                }
            }
            Viewer.print(String.valueOf(ans));
            return true;
        } else {
            int ans = Integer.valueOf(args[1]) * Integer.valueOf(args[2]);
            if (args.length > 2) {
                for (int i = 3; i < args.length; i++) {
                    if (!ArgManipulation.isNumber(args[i])) {
                        Viewer.print("an argument wasn't a number: " + args[i]);
                        return false;
                    }
                    ans *= Integer.valueOf(args[i]);
                }
            }
            Lookup.getMemInstance().add(new MemPair(args[0], String.valueOf(ans)));
            return true;
        }
    }
}
