package ximias.dk.au.cs.fh.Commands;

import com.sun.istack.internal.Nullable;
import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Viewer;

import java.awt.*;

/**
 * Created by Alex on 26/03/2016.
 * Abstraction for all window elements
 */
abstract class WindowElement extends Command {
    private int locX, locY, width, height;
    private String value;
    private Object extra;
    private Color background;
    Component component;
    public abstract Component getComponent();

    WindowElement init(String value, int x, int y, int width, int height,Color background, @Nullable Object extra){
        this.value = value;
        this.locX = x;
        this.locY = y;
        this.width=width;
        this.height=height;
        this.extra=extra;
        this.background=background;
        return this;
    }

    public Color getBackground() {
        return background;
    }

    public Object getExtra() {
        return extra;
    }
    public String getValue(){
        return value;
    }
    public Point getLocation(){
        return new Point(locX,locY);
    }
    public Dimension getSize(){
        return new Dimension(width,height);
    }

    int performChecksGetNameargs(int minlength, String[] args) {
        if (args.length < minlength) {
            Viewer.print("Need at least " + minlength + " arguments");
            return -1;
        }
        int nameargs = 0;
        for (String arg : args) {
            if (!ArgManipulation.isNumber(arg)) {
                nameargs++;
            } else break;
        }
        value = ArgManipulation.argsToString(0, nameargs, args);
        for (int i = nameargs; i < nameargs + 4; i++) {
            if (!ArgManipulation.isNumberAndPositive(args[i])) {
                Viewer.print("Location and size must be numbers and positive.");
                return -1;
            }
        }
        if (Integer.valueOf(args[nameargs + 2]) == 0 || Integer.valueOf(args[nameargs + 3]) == 0) {
            if (!Window.removeFromWindow(value)) {
                Viewer.print("An element with the given name does not exist, so it can't be removed");
                return -1;
            }else{
                return -2;
            }
        }
        return nameargs;
    }
}
