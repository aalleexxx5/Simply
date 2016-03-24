package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Numbers;
import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 24/03/2016.
 */
public class TextColor extends Command {
    @Override
    public boolean execute(String[] args) {
        if(args.length>0) {
            if (Numbers.isNumber(args[0])) {
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
