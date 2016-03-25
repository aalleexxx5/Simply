package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Viewer;

/**
 * Created by Alex on 24/03/2016.
 * Command. Changes the font of the text. Calling it textFont was too easy.
 */
public class TextStyle extends Command {
    @Override
    public String description() {
        return "changes the style of all text.";
    }

    @Override
    public String use(){
        return "textstyle <style> \n styles to try are 'serif', 'sans-serif', 'verdana', 'arial', 'Comic Sans MS'";
    }

    @Override
    public boolean execute(String[] args) {
        if(args.length>0){
            Viewer.setFont(args[0],-1);
            return true;
        }
            Viewer.print("A textlayout was not provided");
            return false;
    }
}
