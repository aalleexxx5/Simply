package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Viewer;

import java.util.ArrayList;

/**
 * Created by Alex on 26/03/2016.
 * Command. Create a window
 */
public class Window extends Command
{
    private static ArrayList<WindowElement> windowElements=new ArrayList<>();
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
        for (String arg:args){
            if (!ArgManipulation.isNumberAndPositive(arg)){
                Viewer.print("Window location and height must be numbers and positive");
                return false;
            }
        }
        Viewer.UpdateWindow(Integer.valueOf(args[0]),Integer.valueOf(args[1]),Integer.valueOf(args[2]),Integer.valueOf(args[3]),windowElements);
        return true;
    }
    public static void addToWindow(WindowElement element){
        for (WindowElement elementInList:windowElements){
            if (elementInList.getValue().equals(element.name())){
                windowElements.remove(elementInList);
                break;
            }
        }
        windowElements.add(element);
        Viewer.updateElements(windowElements);
    }
    static boolean removeFromWindow(String name){
        for (WindowElement element:windowElements) {
            if (element.getValue().equalsIgnoreCase(name)){
                windowElements.remove(element);
                return true;
            }
        }
        return false;
    }
}
