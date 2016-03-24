package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.IFlowchange;
import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.Viewer;

import java.util.ArrayList;

/**
 * Created by Alex on 05/01/2016.
 */
public class Jumpto extends Command {
    private ArrayList<String> lines;
    IFlowchange lookup;
    public Jumpto(IFlowchange lookup){
        this.lookup = lookup;
    }

    @Override
    public boolean execute(String[] args) {
        if (Lookup.getSection().contains(args[0])){
            lookup.setCurrentLines(Lookup.getSection().getLinesAt(args[0]));
            lookup.setFlowChange(true);
            return true;
        }else{
            Viewer.print(args[0] + " was not a valid to jump to point");
            return false;
        }
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }
}