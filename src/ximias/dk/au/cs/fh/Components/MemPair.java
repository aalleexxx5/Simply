package ximias.dk.au.cs.fh.Components;

/**
 * Created by Alex on 05/01/2016.
 */
public class MemPair {
    private String key;
    private String value;
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
