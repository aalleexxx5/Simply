package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.IFlowchange;
import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.Viewer;

import java.util.ArrayList;

/**
 * Created by Alex on 05/01/2016.
 * Command. mimics Goto. Goto is a reserved word in Java.
 */
public class Jumpto extends Command {
    private ArrayList<String> lines;
    private final IFlowchange lookup;
    public Jumpto(IFlowchange lookup){
        this.lookup = lookup;
    }

    @Override
    public String description() {
        return "jumps to a section with the specified name.";
    }

    @Override
    public String use(){
        return "jumpto <destination>";
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        if (Lookup.getSection().contains(args[0])){
            lookup.setCurrentLines(Lookup.getSection().getLinesAt(args[0]));
            lookup.setLastJump(args[0]);
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