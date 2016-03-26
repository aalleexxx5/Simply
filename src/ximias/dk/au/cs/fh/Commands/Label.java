package ximias.dk.au.cs.fh.Commands;

import com.sun.istack.internal.Nullable;
import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Viewer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 26/03/2016.
 */
public class Label extends WindowElement {
    Color color;
    @Override
    public String use() {
        return "label <text> <locationX> <locationY> <width> <height> (optional) colour";
    }

    @Override
    public String description() {
        return "adds a text label to the window";
    }

    @Override
    public Component getComponent() {
        if (component==null){
            JLabel comp = new JLabel(ArgManipulation.toHTML(getValue()));
            if (color!=null) {
                comp.setBackground(color);
                comp.setOpaque(true);
            }
            comp.setBounds(new Rectangle(getLocation(),getSize()));
            component=comp;
        }
        return component;
    }

    WindowElement init(String value, int x, int y, int width, int height, @Nullable String subroutineName,Color color) {
        this.color = color;
        return super.init(value, x, y, width, height, subroutineName);
    }

    @Override
    public boolean execute(String[] args) {
        int nameargs=performChecksGetNameargs(5,args);
        if (nameargs==-1) return false;
        if (nameargs==-2) return true;
        if (args.length>nameargs+4){
            int col;
            try {
                col=Integer.valueOf(args[nameargs+4],16);
            }catch (NumberFormatException e){
                Viewer.print("Color was not propper hexadecimal");
                return false;
            }
            Window.addToWindow(new Label().init(this.getValue(), Integer.valueOf(args[nameargs]), Integer.valueOf(args[nameargs + 1]), Integer.valueOf(args[nameargs + 2]), Integer.valueOf(args[nameargs + 3]), null,new Color(col)));
        }else {
            Window.addToWindow(new Label().init(this.getValue(), Integer.valueOf(args[nameargs]), Integer.valueOf(args[nameargs + 1]), Integer.valueOf(args[nameargs + 2]), Integer.valueOf(args[nameargs + 3]), null));
        }
        return true;
    }
}
