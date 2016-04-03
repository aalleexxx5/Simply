package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.Viewer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by Alex on 01/04/2016.
 * Command. used to get keyboard input
 */
public class Keyboard extends Command {
    private ArrayList<KeyPair> keyPairs = new ArrayList<>();
    @Override
    public String use() {
        return "keyboard <name> <type> <key> <subroutine>\ntypes are typed, down/pressed, up/released";
    }

    @Override
    public String description() {
        return "run subroutine when pressed or released. Similar to button";
    }

    @Override
    public boolean execute(String[] args) {
        if (keyPairs.size()>50){
            Viewer.print("only 50 keys kan be assigned at a time. You have reached the limit!");
            return false;
        }
        KeyEventDispatcher keyEventDispatcher;
        if (args.length==2||args.length==1){
            KeyPair kp = getFromList(args[0]);
            if (kp != null) {
                Viewer.removeKeyboard(getFromList(args[0]).getListener());
                return true;
            }
            return false;
        }
            if (args[1].equalsIgnoreCase("up")||args[1].equalsIgnoreCase("released")){
                keyEventDispatcher = e -> {
                    if (e.getID()==KeyEvent.KEY_RELEASED)
                        if(KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(args[2])) {
                            Lookup.runMainCommand("run", new String[]{args[args.length - 1]});
                            return true;
                        }
                    return false;
                };
            }else if (args[1].equalsIgnoreCase("down")||args[1].equalsIgnoreCase("pressed")){
                keyEventDispatcher = e -> {
                    if (e.getID()==KeyEvent.KEY_PRESSED)
                        if(KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(args[2])) {
                            Lookup.runMainCommand("run", new String[]{args[args.length - 1]});
                            return true;
                        }
                    return false;
                };
            }else if (args[1].equalsIgnoreCase("typed")){
                keyEventDispatcher = e -> {
                    if (e.getID()==KeyEvent.KEY_TYPED)
                        if(KeyEvent.getKeyText(e.getKeyChar()).equalsIgnoreCase(args[2])) {
                            Lookup.runMainCommand("run", new String[]{args[args.length - 1]});
                            return true;
                        }
                    return false;
                };
            }else{
                Viewer.print("no type was found");
                return false;
            }
        keyPairs.add(new KeyPair(args[0],keyEventDispatcher));
        Viewer.addKeyboard(keyEventDispatcher);
        return true;
    }

    private KeyPair getFromList(String name){
        for (KeyPair keyPair : keyPairs) {
            if (keyPair.getName().equals(name)){
                return keyPair;
            }
        }
        Viewer.print("Keyboard "+ name + " didn't exist, so it can't be removed");
        return null;
    }
}
class KeyPair{
    private String name;
    private KeyEventDispatcher listener;
    KeyPair(String name,KeyEventDispatcher listener){
        this.listener=listener;
        this.name=name;
    }

    KeyEventDispatcher getListener() {
        return listener;
    }

    public String getName() {
        return name;
    }
}
