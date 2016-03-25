package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Numbers;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 24/03/2016.
 * Command. Changes the size of the text.
 */
public class TextSize extends Command{
    @Override
    public String description() {
        return "changes all text to be a given size.";
    }

    @Override
    public String use(){
        return "textsize <number> \n The number has to be positive. Larger numbers means larger text";
    }

    @Override
    public boolean execute(String[] args) {
        if(Numbers.isNumber(args[0])){
            Viewer.setFont(null,Integer.valueOf(args[0]));
            return true;
        }
            Viewer.print("Textsize has to be a positive number to work");
            return false;
    }
}
