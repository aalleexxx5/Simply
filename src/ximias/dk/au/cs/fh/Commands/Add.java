package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

/**
 * Created by Alex on 05/01/2016.
 * Command for addition of two numbers.
 */
public class Add extends Command {
    @Override
    public String description() {
        return "Adds two numbers together. (optional) Puts answer in variable <arg1>";
    }

    @Override
    public String use() {
        return "add <arg1> and <arg2>, write result in console \n add <arg2> and <arg3>, result in <arg1>";
    }

    @Override
    public boolean execute(String[] args) { //Add var num ... num OR Add num .. num
        args = Mem.getValuesInArgs(args);
        if (ArgManipulation.isNumber(args[0])) { //option 2
            int ans = 0;
                for (String num : args) {
                    if (!ArgManipulation.isNumber(num)){
                        Viewer.print("an argument wasn't a number: " + num);
                        return false;
                    }
                    ans += Integer.valueOf(num);
                }
                Viewer.print(String.valueOf(ans));
                return true;
        } else {
                int ans = 0;
                for (int i = 1; i < args.length; i++) {
                    if (!ArgManipulation.isNumber(args[i])){
                        Viewer.print("an argument wasn't a number: " + args[i]);
                        return false;
                        }
                    ans += Integer.valueOf(args[i]);
                }
                Lookup.getMemInstance().add(new MemPair(args[0], String.valueOf(ans)));
                return true;
        }
    }
}
