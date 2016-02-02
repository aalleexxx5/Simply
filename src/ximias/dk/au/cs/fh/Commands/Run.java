package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Exechuter;
import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.ThreadedExechuter;
import ximias.dk.au.cs.fh.Components.Viewer;


/**
 * Created by Alex on 14/01/2016.
 */
public class Run extends Command {
    @Override
    public boolean execute(String[] args) {
        if (Lookup.getSubroutine().contains(args[0])){
            new Thread(new ThreadedExechuter(Lookup.getSubroutine().getLinesIn(args[0]))).start();
            return true;
        }
        Viewer.print("The subroutine was not found");
        return false;
    }
}