package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

import java.util.ArrayList;

/**
 * Created by Alex on 05/01/2016.
 * Command. Allows branching
 */
public class If extends Command { // if <value1> <condition> <value2> <event when true>
    private String[] args;
    private final IFlowchange lookup;
    public If(IFlowchange lookup){
        this.lookup = lookup;
    }

    @Override
    public String description() {
        return "Allows branching of the program based on two arguments relation to each other";
    }

    @Override
    public String use(){
        return "if <arg1> <condition> <arg2> <command> <command arg> ... \n conditions are: =, >, <, <=, >=, number and contains";
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        args=ArgManipulation.escape(args);
        this.args = args;
        switch (args[1]) {
            case "="://add a number version of this, so an error is returned if a star is forgotten in a var
                return !args[0].equals(args[2]) || event(3);
            case ">":
                if (ArgManipulation.isNumber(args[0]) && ArgManipulation.isNumber(args[2])) {
                    return Integer.valueOf(args[0]) <= Integer.valueOf(args[2]) || event(3);
                } else {
                    Viewer.print("An argument wasn't a number: " + args[0] + ", " + args[2]);
                    return false;
                }
            case "<":
                if (ArgManipulation.isNumber(args[0]) && ArgManipulation.isNumber(args[2])) {
                    return Integer.valueOf(args[0]) >= Integer.valueOf(args[2]) || event(3);
                } else {
                    Viewer.print("An argument wasn't a number: " + args[0] + ", " + args[2]);
                    return false;
                }
            case ">=":
            case "=>":
                if (ArgManipulation.isNumber(args[0]) && ArgManipulation.isNumber(args[2])) {
                    return Integer.valueOf(args[0]) < Integer.valueOf(args[2]) || event(3);
                } else {
                    Viewer.print("An argument wasn't a number: " + args[0] + ", " + args[2]);
                    return false;
                }
            case "<=":
            case "=<":
                if (ArgManipulation.isNumber(args[0]) && ArgManipulation.isNumber(args[2])) {
                    return Integer.valueOf(args[0]) > Integer.valueOf(args[2]) || event(3);
                } else {
                    Viewer.print("An argument wasn't a number: " + args[0] + ", " + args[2]);
                    return false;
                }
            case "number":
            case "Number":
            case "num":
            case "Num":
                return !ArgManipulation.isNumber(args[0]) || event(2);
            case "contains":
            case "has":
                return !args[0].toLowerCase().contains(args[1].toLowerCase()) || event(3);
            default:
                Viewer.print("the condition was not recognized");
                return false;
        }
    }

    private boolean event(int argnum){ //argnum is the arguments relevant to the event (refer to comment at class signature)
                    String[] a = new String[args.length - (argnum + 1)];
                    for (int i = 0; i < a.length; i++) {
                        a[i] = args[argnum + 1 + i];
                    }
                    if (lookup.runCommand(args[argnum], a))
                        return true;
                    Viewer.print("the command had an error or was unrecognised");
                    return false;
    }
}
