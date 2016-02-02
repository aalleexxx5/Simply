package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.Numbers;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 10/01/2016.
 */
public class Wait extends Command {
    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        if (Numbers.isNumberAndPositive(args[0])) {
            try {
                Thread.sleep(Integer.valueOf(args[0]));
            } catch (InterruptedException e) {}
            return true;
        }
        Viewer.print("unable to wait for negative numbers");
        return false;
    }
}
