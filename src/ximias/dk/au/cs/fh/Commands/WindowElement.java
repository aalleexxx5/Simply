package ximias.dk.au.cs.fh.Commands;

import com.sun.istack.internal.Nullable;
import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Viewer;

import java.awt.*;

/**
 * Created by Alex on 26/03/2016.
 * Abstraction for all window elements
 */
public abstract class WindowElement extends Command {
    private int locX, locY, width, height;
    private String value, subroutineName;
    protected Component component;
    public abstract Component getComponent();

    WindowElement init(String value, int x, int y, int width, int height, @Nullable String subroutineName){
        this.value = value;
        this.locX = x;
        this.locY = y;
        this.width=width;
        this.height=height;
        this.subroutineName=subroutineName;
        return this;
    }
    void setLocation(int x, int y){
        this.locX = x;
        this.locY = y;
    }
    void setSize(int x, int y){
        this.width=x;
        this.height=y;
    }
    public String getSubroutineName(){
        return subroutineName;
    }
    String getValue(){
        return value;
    }
    public Point getLocation(){
        return new Point(locX,locY);
    }
    public Dimension getSize(){
        return new Dimension(width,height);
    }

    protected int performChecksGetNameargs(int minlength, String[] args) {
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
