package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 24/03/2016.
 */
public class TextStyle extends Command {
    @Override
    public boolean execute(String[] args) {
        if(args.length>0){
            Viewer.setFont(args[0],-1);
            return true;
        }
            Viewer.print("A textlayout was not provided");
            return false;
    }
}
