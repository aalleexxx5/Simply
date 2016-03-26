package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 25/03/2016.
 * Command. For resizing the window
 */
public class WindowSize extends Command {
    @Override
    public String use() {
        return "windowsize <width> <height>";
    }

    @Override
    public String description() {
        return "Resizes the window";
    }

    @Override
    public boolean execute(String[] args) {
        if (ArgManipulation.isNumberAndPositive(args[0])&& ArgManipulation.isNumberAndPositive(args[1])){
            Viewer.resizeApp(Integer.valueOf(args[0]),Integer.valueOf(args[1]));
            return true;
        }else if(args[0].equalsIgnoreCase("small")){
            Viewer.resizeApp(433,327);
            return true;
        }else if (args[0].equalsIgnoreCase("medium")){
            Viewer.resizeApp(800,600);
            return true;
        }else if (args[0].equalsIgnoreCase("large")){
            Viewer.resizeApp(1200,800);
            return true;
        }
        Viewer.print("Window can not be resized to "+args[0]);
        return false;
    }
}
