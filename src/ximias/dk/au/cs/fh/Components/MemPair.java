package ximias.dk.au.cs.fh.Components;

/**
 * Created by Alex on 05/01/2016.
 * Key-value singleton for pairing a variable name with the underlying data.
 */
public class MemPair {
    private final String key;
    private final String value;
    public MemPair(String key, String value){
        this.key=key;
        this.value=value;
    }
    public String getKey(){
        return key;
    }

    public String getValue(){
        return value;
    }
}
