package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Viewer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 01/04/2016.
 * Window element Command. Lets you add an image to a window
 */
public class Image extends WindowElement{
    @Override
    public String use() {
        return "image <name> <locx> <locy> <width> <height> <src>";
    }

    @Override
    public String description() {
        return "adds an image to the window";
    }

    @Override
    public boolean execute(String[] args) {
        int nameargs = performChecksGetNameargs(6,args);
        ImageIcon image = new ImageIcon(args[nameargs+4]);
        if (image.getIconHeight()<1) {
            Viewer.print("Image not found");
            return false;
        }
        if (nameargs==-1) return false;
        if (nameargs==-2) return true;
        Window.addToWindow(new Image().init(getValue(),Integer.valueOf(args[nameargs]),Integer.valueOf(args[nameargs+1]),Integer.valueOf(args[nameargs+2]),Integer.valueOf(args[nameargs+3]),null,image));
        return true;
    }

    @Override
    public Component getComponent() {
        if (super.component==null){
            JLabel comp = new JLabel((ImageIcon)getExtra());
            comp.setBounds(new Rectangle(getLocation(),getSize()));
            super.component = comp;
        }
        return super.component;
    }
}
