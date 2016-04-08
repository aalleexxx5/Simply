package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Viewer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Alex on 26/03/2016.
 * Command. Create a window for manipulation.
 */
public class Window extends Command
{
    private static ArrayList<ComponentPair> windowElements=new ArrayList<>();
    @Override
    public String use() {
        return "window <locationX> <locationY> <width> <height> \nTo close the window, use 0 as width or height";
    }
    @Override
    public String description() {
        return "Creates a window with the given parameters";
    }

    @Override
    public boolean execute(String[] args) {
        if (args.length<4){
            Viewer.print("Not enough arguments, need 4.");
            return false;
        }
        for (String arg:args){
            if (!ArgManipulation.isNumberAndPositive(arg)){
                Viewer.print("Window location and height must be numbers and positive");
                return false;
            }
        }
        Viewer.UpdateWindow(Integer.valueOf(args[0]),Integer.valueOf(args[1]),Integer.valueOf(args[2]),Integer.valueOf(args[3]));
        return true;
    }
    private static final ReentrantLock updateLock = new ReentrantLock();
    static void addToWindow(WindowElement element){
        updateLock.lock();
        try {
            if (!contains(element.getValue())){
                ComponentPair component =new ComponentPair(element.getValue(),element.getComponent());
                Viewer.addElement(component.getComp());
                windowElements.add(component);
            }else{
                ComponentPair com = getFromList(element.getValue());
                if (element.getExtra() != null) {
                    if (element.getExtra().equals("text")) {
                        com.drawText(true);
                    } else if (element.getExtra().equals("notext")) {
                        com.drawText(false);
                    }
                }
                com.setBackground(element.getBackground());
                getFromList(element.getValue()).setBounds(new Rectangle(element.getLocation(),element.getSize()));
            }
        }finally {
            updateLock.unlock();
        }
    }
    private static boolean contains(String value){
        for (ComponentPair element:windowElements){
            if (element.getValue().equals(value)){
                return true;
            }
        }
        return false;
    }


    private static ComponentPair getFromList(String name){
        for (ComponentPair element:windowElements){
            if (element.getValue().equals(name)){
                return element;
            }
        }
        throw new NoSuchElementException("Component did not exist in list");
    }
    static boolean removeFromWindow(String name){
        for (ComponentPair element:windowElements) {
            if (element.getValue().equals(name)){
                Viewer.removeElement(element.getComp());
                windowElements.remove(element);
                return true;
            }
        }
        return false;
    }
    public static void removeAll(){
        windowElements = new ArrayList<>();
    }
}
class ComponentPair{
    private final Component comp;
    private final String value;
    ComponentPair(String value, Component comp){
        this.comp=comp;
        this.value=value;
    }
    Component getComp() {
        return comp;
    }
    void setBounds(Rectangle rectangle){
        comp.setBounds(rectangle);
    }
    void setBackground(Color color){
        comp.setBackground(color);
    }
    void drawText(boolean drawtext){
        if (drawtext){
            comp.setFont(UIManager.getDefaults().getFont("Pane.font"));
        }else{
            comp.setFont(new Font(null,Font.PLAIN,0));
        }
    }
    public String getValue() {
        return value;
    }
}