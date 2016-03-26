package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.Viewer;

import javax.swing.*;

/**
 * Created by Alex on 25/03/2016.
 * Command. Displays a message in a popup. Allows specification of message type.
 */
public class Popup extends Command {
    @Override
    public String use() {
        return "popup <title> <type> <message> \n" +
                "<title>: The title of the box\n" +
                "<type>: The type of box you want to open\n" +
                "<message>: The text in the box\n" +
                "valid types are: error, information, warning and question\n" +
                "The type names are invalid in the title.\n" +
                "However nothing is invalid as message\n"+
                "for advanced features, see dialog";
    }

    @Override
    public String description() {
        return "creates a popup with a message and an OK button in the center of the screen";
    }

    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        for (int i =0;i<args.length;i++){
            if (args[i].equalsIgnoreCase("error")){
                JOptionPane.showMessageDialog(null, ArgManipulation.argsToString(i+1,args.length,args), ArgManipulation.argsToString(0,i,args),JOptionPane.ERROR_MESSAGE);
                return true;
            }else if (args[i].equalsIgnoreCase("Information")){
                JOptionPane.showMessageDialog(null, ArgManipulation.argsToString(i+1,args.length,args), ArgManipulation.argsToString(0,i,args),JOptionPane.INFORMATION_MESSAGE);
                return true;
            }else if (args[i].equalsIgnoreCase("warning")){
                JOptionPane.showMessageDialog(null, ArgManipulation.argsToString(i+1,args.length,args), ArgManipulation.argsToString(0,i,args),JOptionPane.WARNING_MESSAGE);
                return true;
            }else if (args[i].equalsIgnoreCase("question")){
                JOptionPane.showMessageDialog(null, ArgManipulation.argsToString(i+1,args.length,args), ArgManipulation.argsToString(0,i,args),JOptionPane.QUESTION_MESSAGE);
                return true;
            }else if (args[i].equalsIgnoreCase("plain")){
                JOptionPane.showMessageDialog(null, ArgManipulation.argsToString(i+1,args.length,args), ArgManipulation.argsToString(0,i,args),JOptionPane.PLAIN_MESSAGE);
                return true;
            }
        }
        Viewer.print("No type was found");
        return false;
    }
}
