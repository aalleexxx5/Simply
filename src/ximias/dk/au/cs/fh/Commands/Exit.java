package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.IFlowchange;

import java.util.ArrayList;

/**
 * Created by Alex on 15/01/2016.
 * Command. Exits the running thread.
 */
public class Exit extends Command {
    private final IFlowchange lookup;
    public Exit(IFlowchange lookup){
        this.lookup = lookup;
    }

    @Override
    public String use() {
        return name();
    }

    @Override
    public String description() {
        return "stops the program. If there are multiple subroutines running, only stops a single subroutine (use endsubroutine for easier to read code)";
    }

    @Override
    public boolean execute(String[] args) {
        lookup.setFlowChange(true);
        lookup.setCurrentLines(new ArrayList<>());
        return true;
    }
}
