package ximias.dk.au.cs.fh.Commands.Graphics;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.Viewer;

import java.util.ArrayList;

/**
 * Created by Alex on 04/04/2016.
 * To simulate if statements in graphics blocks
 */
public class GraphicIf {
    public static int execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        args= ArgManipulation.escape(args);
        switch (args[1]) {
            case "="://add a number version of this, so an error is returned if a star is forgotten in a var
                if (args[0].equals(args[2])) return 3;
                return 0;
            case ">":
                if (ArgManipulation.isNumber(args[0]) && ArgManipulation.isNumber(args[2])) {
                    if (Integer.valueOf(args[0]) > Integer.valueOf(args[2])) return 3;
                    return 0;
                } else {
                    Viewer.print("An argument wasn't a number:" + args[0] + ", " + args[2]);
                    return -1;
                }
            case "<":
                if (ArgManipulation.isNumber(args[0]) && ArgManipulation.isNumber(args[2])) {
                    if (Integer.valueOf(args[0]) < Integer.valueOf(args[2])) return 3;
                    return 0;
                } else {
                    Viewer.print("An argument wasn't a number" + args[0] + ", " + args[2]);
                    return -1;
                }
            case ">=":
            case "=>":
                if (ArgManipulation.isNumber(args[0]) && ArgManipulation.isNumber(args[2])) {
                    if (Integer.valueOf(args[0]) >= Integer.valueOf(args[2])) return 3;
                    return 0;
                } else {
                    Viewer.print("An argument wasn't a number" + args[0] + ", " + args[2]);
                    return -1;
                }
            case "<=":
            case "=<":
                if (ArgManipulation.isNumber(args[0]) && ArgManipulation.isNumber(args[2])) {
                    if (Integer.valueOf(args[0]) <= Integer.valueOf(args[2])) return 3;
                    return 0;
                } else {
                    Viewer.print("An argument wasn't a number" + args[0] + ", " + args[2]);
                    return -1;
                }
            case "number":
            case "Number":
            case "num":
            case "Num":
                if (ArgManipulation.isNumber(args[0])) return 2;
                return 0;
            case "contains":
            case "has":
                if (args[0].toLowerCase().contains(args[1].toLowerCase())) return 2;
                return 0;
            default:
                Viewer.print("the condition was not recognized");
                return -1;
        }
    }

}
