package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Commands.Graphics.Graphics;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 04/04/2016.
 * Command to draw a graphics element on screen, buffered or not.
 */
public class Draw extends Command {

    @Override
    public String use() {
        return "draw <name> <locX> <locY>";
    }

    @Override
    public String description() {
        return "draw the content of a graphics block";
    }

    @Override
    public boolean execute(String[] args) throws InterruptedException {
        return Viewer.drawElement(Graphics.getImage(args[0]));
    }
}
