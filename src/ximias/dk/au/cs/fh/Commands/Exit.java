package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.IFlowchange;

import java.util.ArrayList;

/**
 * Created by Alex on 15/01/2016.
 */
public class Exit extends Command {
    IFlowchange lookup;
    public Exit(IFlowchange lookup){
        this.lookup = lookup;
    }
    @Override
    public boolean execute(String[] args) {
        lookup.setFlowChange(true);
        lookup.setCurrentLines(new ArrayList<>());
        return true;
    }
}
