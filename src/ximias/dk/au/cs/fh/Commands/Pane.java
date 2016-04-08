package ximias.dk.au.cs.fh.Commands;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Alex on 26/03/2016.
 * Command. For creating JPanes with elements inside. for those who don't think windows are hard enough
 * Not implemented yet
 */
public class Pane extends WindowElement {//TODO: add elements to this
    private static final ArrayList<WindowElement> windowElements=new ArrayList<>();
    Color color;
    private String[] components;

    @Override
    public String use() {
        return "pane <name> <locationX> <locationY> <width> <height> (optional) <element> ... <elenemt>";
    }

    @Override
    public String description() {
        return "create a pane to contain elements or colours. This way elements can be moved as a group";
    }

    @Override
    public Component getComponent() {
        JLayeredPane comp;
        if (component==null){
            comp = new JLayeredPane();
            comp.setBackground(Color.black);
            comp.setBounds(new Rectangle(getLocation(),getSize()));
            comp.setOpaque(true);
        }else{
            return component;
        }
        for (String component:components){
            //windowElements.add(Window.getFromList(component));
            Window.removeFromWindow(component);
        }
        for (WindowElement element: windowElements){
            comp.add(element.getComponent(),1);
        }
        component=comp;
        return component;
    }

    @Override
    public boolean execute(String[] args) {
        int nameargs = performChecksGetNameargs(5,args);
        if (nameargs==-1) return false;
        if (nameargs==-2) return true;
//        Window.addToWindow(new Pane().init(getValue(),Integer.valueOf(args[nameargs]),Integer.valueOf(args[nameargs+1]),Integer.valueOf(args[nameargs+2]),Integer.valueOf(args[nameargs+3]),new Color(Integer.valueOf(args[nameargs+4],16)),nameargs,new String[] {}));
        return true;
    }
}
