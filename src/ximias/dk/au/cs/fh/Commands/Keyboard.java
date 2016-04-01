package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.Viewer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by Alex on 01/04/2016.
 * Command. used to get keyboard input
 */
public class Keyboard extends Command {
    private ArrayList<KeyPair> keyPairs = new ArrayList();
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
        KeyListener keyListener;
        if (args.length==2||args.length==1){
            KeyPair kp = getFromList(args[0]);
            if (kp != null) {
                Viewer.removeKeyboard(getFromList(args[0]).getListener());
                return true;
            }
            return false;
        }
            if (args[1].equalsIgnoreCase("up")||args[1].equalsIgnoreCase("released")){
                keyListener = new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        if(KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(args[2])) {
                            Lookup.runMainCommand("run", new String[]{args[args.length - 1]});
                        }
                    }
                };
            }else if (args[1].equalsIgnoreCase("down")||args[1].equalsIgnoreCase("pressed")){
                keyListener = new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(args[2])) {
                            Lookup.runMainCommand("run", new String[]{args[args.length - 1]});
                        }
                    }
                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                };
            }else if (args[1].equalsIgnoreCase("typed")){
                keyListener = new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if(String.valueOf(e.getKeyChar()).equalsIgnoreCase(args[2])) {
                        Lookup.runMainCommand("run", new String[]{args[args.length - 1]});
                    }
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                };
            }else{
                Viewer.print("no type was found");
                return false;
            }
        keyPairs.add(new KeyPair(args[0],keyListener));
        Viewer.addKeyboard(keyListener);
        return true;
    }

    KeyPair getFromList(String name){
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
    private KeyListener listener;
    KeyPair(String name,KeyListener listener){
        this.listener=listener;
        this.name=name;
    }

    public KeyListener getListener() {
        return listener;
    }

    public String getName() {
        return name;
    }
}
