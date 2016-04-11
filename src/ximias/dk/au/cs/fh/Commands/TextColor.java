package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 24/03/2016.
 * Command. Changes the colour of the text in console.
 */
public class TextColor extends Command {
    @Override
    public String description() {
        return "changes all text to a certain color";
    }

    @Override
    public String use(){
        return "textcolor <color> \n valid colorvalues are as hex (0-ffffff) or number (0-16777215) \n every four bits describes each color";
    }

    @Override
    public boolean execute(String[] args) {
        if(args.length>0) {
            if (ArgManipulation.isNumber(args[0])) {
                Viewer.setTextColour(Integer.valueOf(args[0]));
                return true;
            }else {
                try {
                    Viewer.setTextColour(Integer.parseInt(args[0], 16));
                    return true;
                }catch (NumberFormatException e){
                    Viewer.print("The number was not proper Hexadecimal");
                    return false;
                }
            }
        }
        Viewer.print("A colour value has to be provided");
        return false;
    }
}
