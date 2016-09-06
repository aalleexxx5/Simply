package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.Mem;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 26/03/2016.
 * Window Component command. Adds a button to the window
 */
public class Button extends WindowElement {
    @Override
    public String use() {
        return "button <name> <locationX> <locationY> <width> <height> (optional) <subroutine>\nruns the subroutine when the button is pressed";
    }

    @Override
    public String description() {
        return "Add a custom button to the window. Remove it if width or height is 0";
    }

    @Override
    public Component getComponent() {
        if (super.component==null){
            JButton comp=new JButton();
            comp.setText(ArgManipulation.toHTML(getValue()));
            comp.setBounds(new Rectangle(getLocation(),getSize()));
            if (getExtra()!=null){
                comp.addActionListener(e -> Lookup.runMainCommand("run", new String[]{String.valueOf(getExtra())}));
            }
            super.component=comp;
        }
        return super.component;
    }

    @Override
    public boolean execute(String[] args) {
        args= Mem.getValuesInArgs(args);
        int nameargs = performChecksGetNameargs(5,args);
        if (nameargs==-1) return false;
        if (nameargs==-2) return true;
        WindowElement element;
        if (args.length>nameargs+4) {
            element = new Button().init(getValue(), Integer.valueOf(args[nameargs]), Integer.valueOf(args[nameargs+1]), Integer.valueOf(args[nameargs+2]), Integer.valueOf(args[nameargs+3]),null,args[nameargs+4]);
        }else{
            element = new Button().init(getValue(), Integer.valueOf(args[nameargs]), Integer.valueOf(args[nameargs+1]), Integer.valueOf(args[nameargs+2]), Integer.valueOf(args[nameargs+3]),null,null);
        }
        Window.addToWindow(element);
        return true;
    }
}
