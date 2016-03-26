package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Viewer;

import javax.swing.*;

/**
 * Created by Alex on 26/03/2016.
 * Command. Opens a dialog box with configurable options.
 */
public class Dialog extends Command {//TODO: Implement this

    @Override
    public String use() {
        /* decode this by looping through args till a valid type name is found.
        * everything between arg[0] and this is the message.
        * Loop backwards till an invalid option is found, the arg to the left of that is the var name
        * everything between this and type is the title.
        */
        return "dialog <message> <type> <title> <variableName> <options> ... <options> \n" +
                "<message>: The text in the box\n" +
                "<title>: the title at the top of the box\n"+
                "<type>: the type of message. valid types are: plain, error, information, warning and question\n" +
                "<variableName>: a name for a variable containing what the user pressed \n" +
                "<options>: the options of the box, every argument adds one more button";
    }

    @Override
    public String description() {
        return "Shows a dialog box in the center of the screen. This is an advanced version of popup";
    }

    @Override
    public boolean execute(String[] args) {
        Viewer.print("Dialog is not yet implemented");
        return false;
        /*
        if (args.length<5){
            Viewer.print("Not enough arguments. needs min. 5");
            return false;
        }
        if (args[2].equalsIgnoreCase("error")){
            showDialog(JOptionPane.ERROR_MESSAGE,args);
        }
        if (args[2].equalsIgnoreCase("information")){
            showDialog(JOptionPane.INFORMATION_MESSAGE,args);
        }
        if (args[2].equalsIgnoreCase("warning")){
            showDialog(JOptionPane.WARNING_MESSAGE,args);
        }
        if (args[2].equalsIgnoreCase("question")){
            showDialog(JOptionPane.QUESTION_MESSAGE,args);
        }
        if (args[2].equalsIgnoreCase("plain")){
            showDialog(JOptionPane.PLAIN_MESSAGE,args);
        }
        return false;
        */
    }

    private void showDialog(int messageType, String[] args){
        Object[] options = new Object[args.length-4];
        System.arraycopy(args, 4, options, 0, args.length - 4);
        JOptionPane.showOptionDialog(null, args[0], args[1],JOptionPane.DEFAULT_OPTION, messageType,null, options, options[0]);
    }
}
