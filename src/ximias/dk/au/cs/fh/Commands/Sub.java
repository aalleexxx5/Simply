package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

/**
 * Created by Alex on 05/01/2016.
 * Command. Subtracts numbers.
 */
public class Sub extends Command {
    @Override
    public String description() {
        return "Subtract two numbers or remove start of text.";
    }

    @Override
    public String use() {
        return "Numbers:\nsub <num1> <num2> (the same as <num1> - <num2>) \nsub <varName> <num> <num>\nText:\nsub <varName> <location/symbol> <data>";
    }

    @Override
    public boolean execute(String[] args) { //Add var num ... num OR Add num .. num
        args = Mem.getValuesInArgs(args);
        if (args.length<3) {
            Viewer.print("not enough arguments");
            return false;
        }
        if (args.length<4&&ArgManipulation.isNumber(args[1])) {
            if (ArgManipulation.isNumber(args[0])) {
                Viewer.print(String.valueOf(Integer.valueOf(args[0])-Integer.valueOf(args[1])));
                return true;
            }
            if(ArgManipulation.isNumber(args[2])) {
                Lookup.getMemInstance().add(new MemPair(args[0], String.valueOf(Integer.valueOf(args[1]) - Integer.valueOf(args[2]))));
                return true;
            }
        }
        String data = ArgManipulation.argsToString(2,args.length,args);
        if (ArgManipulation.isNumber(args[1])){
            Lookup.getMemInstance().add(new MemPair(args[0],data.substring(Integer.valueOf(args[1]))));
            return true;
        }else{
            if (args[1].equals(Constants.NO_SPACE_SYMBOL)) args[1]=" ";
            if (!data.contains(args[1])){
                Viewer.print(args[1] + " was not found");
                return false;
            }
            Lookup.getMemInstance().add(new MemPair(args[0],data.substring(data.indexOf(args[1]))));
            return true;
        }

    }
}