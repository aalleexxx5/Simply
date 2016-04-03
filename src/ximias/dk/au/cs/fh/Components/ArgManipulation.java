package ximias.dk.au.cs.fh.Components;

/**
 * Created by Alex on 05/01/2016.
 * You can ask this class about everything useful relating to numbers
 */
public class ArgManipulation {
    public static boolean isNumber(String n){
        try{
            //noinspection ResultOfMethodCallIgnored
            Integer.valueOf(n);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    public static boolean isNumberAndPositive(String n){
        try {
            return (Integer.valueOf(n) >= 0);
        }catch (NumberFormatException e){
            return false;
        }
    }
    public static boolean isHex(String n){
        try{
            //noinspection ResultOfMethodCallIgnored
            Integer.parseInt(n,16);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }
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
    public static String[] escape(String[] args){
        for (int i = 0; i < args.length; i++) {
            args[i]=args[i].replaceAll(Constants.NEWLINE_SYMBOL,"\n");
            args[i]=args[i].replaceAll(Constants.ESCAPE_STAR,"*");
        }
        return args;
    }
    public static String toHTML(String text){
        return "<html>"+text.replaceAll("\n","<br>")+"</html>";
    }
}
