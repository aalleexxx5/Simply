package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

/**
 * Created by Alex on 03/04/2016.
 * Command. add text area to frame.
 */
public class Textarea extends WindowElement {
    @Override
    public String use() {
        return "textfield <name> <LocationX> <locationY> <width> <height> <varName> (optional) <subroutine> (opt.) <actionText> (opt.) color\n<subroutine>: subroutine to run every time <actionText> is typed\nIf no actionText is provided, runs when variable is updated.";
    }

    @Override
    public String description() {
        return "adds a text area for the user to input stuff in to the window. Continuously updates a variable";
    }

    @Override
    public Component getComponent() {
        if (component == null) {
            JTextArea com = new JTextArea();
            com.setBounds(new Rectangle(getLocation(),getSize()));
            String[] extras = (String[])getExtra();
            int subroutine=-1;
            // Locate <color> and <subroutine>
            for (int i = 1; i < extras.length; i++) {
                if (subroutine==-1&&!ArgManipulation.isHex(extras[i])){
                    subroutine=i;
                }
                if (extras[i].length()==6&&ArgManipulation.isHex(extras[i])){
                    com.setBackground(new Color(Integer.parseInt(extras[i],16)));
                }else if (extras[i].length()==9&&ArgManipulation.isNumber(extras[i])){
                    com.setBackground(new Color(Integer.valueOf(extras[1])));
                }
            }
            final int subnum = subroutine;
            // add actionListener to update variable and run the subroutine
            if (subroutine!=-1){
                if (extras.length>subnum+1&&!ArgManipulation.isHex(extras[subnum+1])) {
                    extras[subnum+1]= extras[subnum+1].replaceAll(Constants.NEWLINE_SYMBOL,"\n");
                    com.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                        }

                        @Override
                        public void keyPressed(KeyEvent e) {

                        }

                        @Override
                        public void keyReleased(KeyEvent e) {
                            if (com.getText().endsWith(extras[subnum+1])) {
                                Lookup.runMainCommand("run", new String[]{extras[subnum]});
                            }
                            Lookup.getMemInstance().add(new MemPair(extras[0], com.getText()));
                        }
                    });
                }else{
                    com.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                        }

                        @Override
                        public void keyPressed(KeyEvent e) {

                        }

                        @Override
                        public void keyReleased(KeyEvent e) {
                                Lookup.runMainCommand("run", new String[]{extras[subnum]});
                            Lookup.getMemInstance().add(new MemPair(extras[0], com.getText()));
                        }
                    });
                }
            }else{
                com.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        Lookup.getMemInstance().add(new MemPair(extras[0],com.getText()));
                    }
                });
            }
            component=com;
        }
        return component;
    }

    @Override
    public boolean execute(String[] args) {
        args= Mem.getValuesInArgs(args);
        for (int i = 1; i < 1 + 4; i++) {
            if (!ArgManipulation.isNumberAndPositive(args[i])) {
                Viewer.print("Location and size must be numbers and positive.");
                return false;
            }
        }
        if (Integer.valueOf(args[1 + 2]) == 0 || Integer.valueOf(args[1 + 3]) == 0) {
            if (!Window.removeFromWindow(args[0])) {
                Viewer.print("An element with the given name does not exist, so it can't be removed");
                return false;
            }
            return true;
        }
        Color color=Color.WHITE;
        for (int i = 5; i < args.length; i++) {
            if (args[i].length()==6&&ArgManipulation.isHex(args[i])){
                color=new Color(Integer.parseInt(args[i],16));
                break;
            }else if (args[i].length()==9&&ArgManipulation.isNumber(args[i])){
                color=new Color(Integer.valueOf(args[i]));
                break;
            }
        }
        Window.addToWindow(init(args[0],Integer.valueOf(args[1]),Integer.valueOf(args[2]),Integer.valueOf(args[3]),Integer.valueOf(args[4]),color, Arrays.copyOfRange(args,5,args.length)));
        return true;
    }
}
