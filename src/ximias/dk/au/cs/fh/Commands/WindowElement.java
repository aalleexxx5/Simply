package ximias.dk.au.cs.fh.Commands;

import com.sun.istack.internal.Nullable;

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

}
