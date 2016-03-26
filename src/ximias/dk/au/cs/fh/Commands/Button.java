package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.Viewer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 26/03/2016.
 * Window Component command. Adds a button to the window
 */
public class Button extends WindowElement {
    @Override
    public String use() {
        return "button <name> <locationX> <locationY> <width> <height> <subroutine>\nruns the subroutine when the button is pressed";
    }

    @Override
    public String description() {
        return "Add a custom button to the window. Remove it if width or height is 0";
    }

    @Override
    public Component getComponent() {
        if (super.component==null){
            JButton comp=new JButton();
            comp.setText(super.getValue());
            comp.setBounds(new Rectangle(super.getLocation(),super.getSize()));
            if (!getSubroutineName().isEmpty()){
                comp.addActionListener(e -> {
                    Lookup.runMainCommand("run", new String[]{getSubroutineName()});
                });
            }
            super.component=comp;
        }
        return super.component;
    }

    @Override
    public boolean execute(String[] args) {
        if (args.length<4){
            Viewer.print("Need at least 4 arguments");
            return false;
        }
        for (int i =1;i<5;i++){
            if(!ArgManipulation.isNumberAndPositive(args[i])){
                Viewer.print("Button Location and size must be numbers and positive.");
                return false;
            }
        }
        if (Integer.valueOf(args[3])==0||Integer.valueOf(args[4])==0){
            if (!Window.removeFromWindow(args[0])){
                Viewer.print("A button with the given name does not exist, so it can't be removed");
                return false;
            }
            return true;
        }
        WindowElement element;
        if (args.length>4) {
            element = new Button().init(args[0], Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4]),args[5]);
        }else{
            element = new Button().init(args[0], Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4]),null);
        }
        Window.addToWindow(element);
        return true;
    }
}
