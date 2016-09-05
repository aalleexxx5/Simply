package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.ThreadedExechuter;
import ximias.dk.au.cs.fh.Components.Viewer;

//import ximias.dk.au.cs.fh.Main;

/**
 * Created by Alex on 24/03/2016.
 * Command. Executes a subroutine, waits for finish.
 */
public class Execute extends Command {

    @Override
    public String description() {
        return "Runs a subroutine and waits for it to finish before continuing";
    }

    @Override
    public String use(){
        return "execute <subroutine>";
    }

    @Override
    public boolean execute(String[] args) {
        if (Lookup.getSubroutine().contains(args[0])) {
            Thread exeThread = new Thread(new Thread(new ThreadedExechuter(Lookup.getSubroutine().getLinesIn(args[0]),args[0]),args[0]));
            exeThread.setName(args[0]+Run.numThreads());
            exeThread.start();
            try {
                exeThread.join();
            } catch (InterruptedException ignored) {
                Viewer.print("Program was terminated while waiting for an execute to finish");
                return true;
            }
            return true;
        }
        Viewer.print("The Subroutine "+args[0]+" was not found");
        return false;
    }
}
