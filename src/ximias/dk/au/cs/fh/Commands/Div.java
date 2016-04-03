package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;


/**
 * Created by Alex on 05/01/2016.
 * Command for dividing arguments.
 */
public class Div extends Command {
    @Override
    public String description() {
        return "Divides a number by another OR Divides text either at a position or at a letter/symbol";
    }

    @Override
    public String use() {
        return "Numbers:\nDivide <number1> <number2>. result goes in console \n Divide <varName> <number2> <number3>\nText:\ndiv <varName1> <varName2> <position/symbol> <text>\nThe position/symbol goes in varName2";
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        if (args.length<4&&ArgManipulation.isNumber(args[1])) {
            if (ArgManipulation.isNumber(args[0])) {
                Viewer.print(String.valueOf(Integer.valueOf(args[0])/Integer.valueOf(args[1])));
                return true;
            }
            if(ArgManipulation.isNumber(args[2])) {
                Lookup.getMemInstance().add(new MemPair(args[0], String.valueOf(Integer.valueOf(args[1]) / Integer.valueOf(args[2]))));

                return true;
            }
            return false;
        } else {//text manipulation
            String data = ArgManipulation.argsToString(3,args.length,args);
            String s1;
            String s2;
            if (ArgManipulation.isNumber(args[2])){
                s1 = data.substring(0,Integer.valueOf(args[2]));
                s2 = data.substring(Integer.valueOf(args[2]));
            }else{
                if (args[2].equals(Constants.NO_SPACE_SYMBOL)) args[2]=" ";
                if (!data.contains(args[2])){
                    Viewer.print(args[2] + " was not found");
                    return false;
                }
                s1 = data.substring(0,data.indexOf(args[2]));
                s2 = data.substring(data.indexOf(args[2]));
            }
            Lookup.getMemInstance().add(new MemPair(args[0],s1));
            Lookup.getMemInstance().add(new MemPair(args[1],s2));
            return true;
        }
    }
}