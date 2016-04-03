package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.Optional;

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
        return "dialog <message> <type> <title> <variableName> <selectedOption> <options> ... <options> \n" +
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
        args= Mem.getValuesInArgs(args);
        if (args.length<5){
            Viewer.print("Not enough arguments. needs min. 5");
            return false;
        }
        int messageArgs=-1,varPos=-1,type=JOptionPane.PLAIN_MESSAGE;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("error")){
                messageArgs=i;
                type=JOptionPane.ERROR_MESSAGE;
                i=i+2;
            }else if (args[i].equalsIgnoreCase("Information")){
                messageArgs=i;
                type=JOptionPane.INFORMATION_MESSAGE;
                i=i+2;
            }else if (args[i].equalsIgnoreCase("warning")){
                messageArgs=i;
                type=JOptionPane.WARNING_MESSAGE;
                i=i+2;
            }else if (args[i].equalsIgnoreCase("question")){
                messageArgs=i;
                type=JOptionPane.QUESTION_MESSAGE;
                i=i+2;
            }else if (args[i].equalsIgnoreCase("plain")){
                messageArgs=i;
                type=JOptionPane.PLAIN_MESSAGE;
                i=i+2;
            }
            if (messageArgs != -1&& ArgManipulation.isNumber(args[i])) {
                varPos=i;
                break;
            }
        }
        if (varPos==-1){
            Viewer.print("Missing type and/or selected option");
            return false;
        }
        int value = JOptionPane.showOptionDialog(null,ArgManipulation.argsToString(0,messageArgs,args),ArgManipulation.argsToString(messageArgs+1,varPos-1,args),JOptionPane.DEFAULT_OPTION,type,null, Arrays.copyOfRange(args,varPos+1,args.length),args[varPos]);
        Lookup.getMemInstance().add(new MemPair(args[varPos-1],args[value+varPos+1]));
        return true;
    }
}
