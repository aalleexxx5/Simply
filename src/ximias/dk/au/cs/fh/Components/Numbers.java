package ximias.dk.au.cs.fh.Components;

/**
 * Created by Alex on 05/01/2016.
 */
public class Numbers {
    public static boolean isNumber(String n){
        try{
            Integer.valueOf(n);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    public static boolean isNumberAndPositive(String n){
        try {
            return (Integer.valueOf(n) > 0);
        }catch (NumberFormatException e){
            return false;
        }
    }
}
