package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.IFlowchange;

/**
 * Created by Alex on 05/01/2016.
 * Command, Ends a subroutine
 */
public class endsubroutine extends Command {
    private final IFlowchange lookup;
    public endsubroutine(IFlowchange lookup){
        this.lookup=lookup;
    }

    @Override
    public String description() {
        return "Ends a subroutine. This needs to come after a subroutine command";
    }

    @Override
    public String use(){
        return "use to end a subroutine. A subroutine contains the code between subroutine and endsubroutine";
    }

    @Override
    public boolean execute(String[] args) {
        lookup.runCommand("exit",new String[0]);
        return true;
    }
}
