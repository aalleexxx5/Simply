package ximias.dk.au.cs.fh.Commands;

/**
 * Created by Alex on 05/01/2016.
 */
public abstract class Command {
    public String name(){
        return this.getClass().getSimpleName();
    }

    public abstract boolean execute(String[] args);
}
