package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

import java.util.ArrayList;

/**
 * Created by Alex on 05/01/2016.
 */
public class If extends Command { // if <value1> <condition> <value2> <event when true>
    String[] args;
    IFlowchange lookup;
    public If(IFlowchange lookup){
        this.lookup = lookup;
    }
    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        this.args = args;
        switch (args[1]){
            case "=":
                if(args[0].equals(args[2]))
                    return event(3,true);
                return event(3,false);
            case ">":
                if(Numbers.isNumber(args[0])&&Numbers.isNumber(args[2])){
                    if(Integer.valueOf(args[0])>Integer.valueOf(args[2]))
                        return event(3,true);
                    return event(3,false);
                }else{
                    Viewer.print("An argument wasn't a number");
                    return false;
                }
            case "<":
                if(Numbers.isNumber(args[0])&&Numbers.isNumber(args[2])){
                    if(Integer.valueOf(args[0])<Integer.valueOf(args[2]))
                        return event(3,true);
                    return event(2,false);
                }else{
                    Viewer.print("An argument wasn't a number");
                    return false;
                }
            case ">=":
            case "=>":
                if(Numbers.isNumber(args[0])&&Numbers.isNumber(args[2])){
                    if(Integer.valueOf(args[0])>=Integer.valueOf(args[2]))
                        return event(3,true);
                    return event(3,false);
                }else{
                    Viewer.print("An argument wasn't a number");
                    return false;
                }
            case "<=":
            case "=<":
                if(Numbers.isNumber(args[0])&&Numbers.isNumber(args[2])){
                    if(Integer.valueOf(args[0])<=Integer.valueOf(args[2]))
                        return event(3,true);
                    return event(3,false);
                }else{
                    Viewer.print("An argument wasn't a number");
                    return false;
                }
            case "number":
            case "Number":
            case "num":
            case "Num":
                if (Numbers.isNumber(args[0]))
                    return event(2, true);
                return event(2, false);
            case "contains":
            case "has":
                if (args[0].toLowerCase().contains(args[1].toLowerCase())) {
                    return event(3, true);
                }else {
                    return event(3, false);
                }
            default:
                Viewer.print("the condition was not recognized");
                return false;
        }
    }

    private boolean event(int argnum, boolean eventstate){ //argnum is the arguments relevant to the event (refer to comment at class signature) eventstate is weather the statement evaluated to true
        switch (args[argnum]){
            case "skip":
            case "SKIP":
                if(eventstate) {
                    lookup.setFlowChange(true);
                    try {
                        lookup.setCurrentLines(new ArrayList<>(Lookup.getLines().subList(Integer.parseInt(args[argnum + 1])+1, Lookup.getLines().size())));
                    } catch (IndexOutOfBoundsException e) {
                        Viewer.print("Skipped more lines than was available");
                        return false;
                    }
                    return true;
                }else{
                    return true;
                }
            case "do":
                if (eventstate){
                    return true;
                }else{
                    lookup.setFlowChange(true);
                    try {
                        lookup.setCurrentLines(new ArrayList<>(Lookup.getLines().subList(Integer.parseInt(args[argnum + 1])+1, Lookup.getLines().size())));
                    } catch (IndexOutOfBoundsException e) {
                        Viewer.print("Skipped more lines than was available");
                        return false;
                    }
                    return true;
                }
            default:
                if (eventstate) {
                    String[] a = new String[args.length - (argnum + 1)];
                    for (int i = 0; i < a.length; i++) {
                        a[i] = args[argnum + 1 + i];
                    }
                    if (lookup.runCommand(args[argnum], a))
                        return true;
                    Viewer.print("the command had an error or was unrecognised");
                    return false;
                }else{
                    return true;
                }
        }
    }
}
