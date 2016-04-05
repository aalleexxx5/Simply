package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.IFlowchange;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 10/01/2016.
 * Command. Makes current thread sleep.
 */
public class Wait extends Command {
    IFlowchange lookup;
    public Wait(IFlowchange lookup){
        this.lookup=lookup;
    }
    @Override
    public String description() {
        return "waits a specified amount of milliseconds";
    }

    @Override
    public String use(){
        return "wait <time>";
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        lookup.waiting();
        if (ArgManipulation.isNumberAndPositive(args[0])) {
            try {
                Thread.sleep(Integer.valueOf(args[0]));
            } catch (InterruptedException ignored) {}
            return true;
        }
        Viewer.print("unable to wait for negative numbers");
        return false;
    }
}
