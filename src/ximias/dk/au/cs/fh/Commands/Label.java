package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.Viewer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 26/03/2016.
 * Command. Adds a label to the window
 */
public class Label extends WindowElement {
    @Override
    public String use() {
        return "label <text> <locationX> <locationY> <width> <height> (optional) <colour> (optional) <displayName>";
    }

    @Override
    public String description() {
        return "adds a text label to the window";
    }

    @Override
    public Component getComponent() {
        if (component==null){
            JLabel comp;
            if (getExtra() != null&&getExtra().equals("notext")){
                comp = new JLabel();
            }else{
                comp = new JLabel(ArgManipulation.toHTML(getValue()));
            }
            comp.setBackground(getBackground());
            comp.setOpaque(true);
            comp.setBounds(new Rectangle(getLocation(),getSize()));
            component=comp;
        }
        return component;
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        int nameargs=performChecksGetNameargs(5,args);
        if (nameargs==-1) return false;
        if (nameargs==-2) return true;
        if (args.length>nameargs+4){
            Color col;
            if (args[nameargs+4].length()==9&&ArgManipulation.isNumber(args[nameargs]+4)){
                col = new Color(Integer.valueOf(args[nameargs + 4].substring(0,3)),Integer.valueOf(args[nameargs + 4].substring(3,6)),Integer.valueOf(args[nameargs + 4].substring(6)));
            }else {
                try {
                    col = new Color(Integer.valueOf(args[nameargs + 4], 16));
                } catch (NumberFormatException e) {
                    Viewer.print("Color "+args[nameargs+4]+" was not proper hexadecimal");
                    return false;
                }
            }
            if (args[args.length-1].equals("false")||args[args.length-1].equals("no")){
                Window.addToWindow(new Label().init(this.getValue(), Integer.valueOf(args[nameargs]), Integer.valueOf(args[nameargs + 1]), Integer.valueOf(args[nameargs + 2]), Integer.valueOf(args[nameargs + 3]),col ,"notext"));
            }else if (args[args.length-1].equals("false")||args[args.length-1].equals("no")){
                Window.addToWindow(new Label().init(this.getValue(), Integer.valueOf(args[nameargs]), Integer.valueOf(args[nameargs + 1]), Integer.valueOf(args[nameargs + 2]), Integer.valueOf(args[nameargs + 3]),col ,"text"));
            }
                Window.addToWindow(new Label().init(this.getValue(), Integer.valueOf(args[nameargs]), Integer.valueOf(args[nameargs + 1]), Integer.valueOf(args[nameargs + 2]), Integer.valueOf(args[nameargs + 3]),col ,null));
        }else {
            Window.addToWindow(new Label().init(this.getValue(), Integer.valueOf(args[nameargs]), Integer.valueOf(args[nameargs + 1]), Integer.valueOf(args[nameargs + 2]), Integer.valueOf(args[nameargs + 3]),Color.white, null));
        }
        return true;
    }
}
