package ximias.dk.au.cs.fh.Components;

/**
 * Created by Alex on 05/01/2016.
 * Contains utility functions relating to manipulating arguments
 */
public class ArgManipulation {
    /**
     * Returns weather <b>candidate</b> is a number.
     * @param candidate candidate to check
     * @return true if candidate is a number
     */
    public static boolean isNumber(String candidate){
        try{
            //noinspection ResultOfMethodCallIgnored
            Integer.valueOf(candidate);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * Returns weather candidate is a number
     * @param candidate value to check
     * @return true if candidate is a number and positive
     */
    public static boolean isNumberAndPositive(String candidate){
        try {
            return (Integer.valueOf(candidate) >= 0);
        }catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Returns weather candidate is a valid hexadecimal number
     * @param candidate value to check
     * @return true if candidate is a valid hex number
     */
    public static boolean isHex(String candidate){
        try{
            //noinspection ResultOfMethodCallIgnored
            Long.parseLong(candidate,16);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * Creates a string from a string array of arguments, escaping and looking up variables
     * @param start The index to start at
     * @param stop The index to end at
     * @param args The string array to convert
     * @return Returns a string from the inputted array
     */
    public static String argsToString(int start, int stop,String[] args) {
        String value = "";
        for (int i = start; i < stop; i++) {
            args[i] = args[i].replace(Constants.ESCAPE_STAR, "*");
            if (i + 1 >= args.length || args[i + 1].contains(Constants.NO_SPACE_SYMBOL)) {
                value += args[i];
                i++;
            } else {
                value += args[i] + " ";
            }
        }
        value = value.replaceAll(Constants.NEWLINE_SYMBOL, "\n");
        return value;
    }

    /**
     * Escapes the text for text displayed on screen
     * @param args the arguments to escape
     * @return the escaped text
     */
    public static String[] escape(String[] args){
        for (int i = 0; i < args.length; i++) {
            args[i]=args[i].replaceAll(Constants.NEWLINE_SYMBOL,"\n");
            args[i]=args[i].replaceAll(Constants.ESCAPE_STAR,"*");
        }
        return args;
    }

    /**
     * Escapes text as HTML for user interfaces
     * @param text the input
     * @return string formatted in HTML
     */
    public static String toHTML(String text){
        return "<html>"+text.replaceAll("\n","<br/>")+"</html>";
    }
}
