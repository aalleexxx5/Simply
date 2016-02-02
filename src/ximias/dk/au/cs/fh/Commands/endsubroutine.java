package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.IFlowchange;
import ximias.dk.au.cs.fh.Components.Viewer;

import javax.swing.text.View;

/**
 * Created by Alex on 05/01/2016.
 */
public class endsubroutine extends Command {
    IFlowchange lookup;
    public endsubroutine(IFlowchange lookup){
        this.lookup=lookup;
    }
    @Override
    public boolean execute(String[] args) {
        lookup.runCommand("exit",new String[0]);
        return true;
    }
}
