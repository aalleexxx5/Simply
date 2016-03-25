package ximias.dk.au.cs.fh.Commands;

/**
 * Created by Alex on 05/01/2016.
 * Command Abstract class, must be implemented be all commands
 */
public abstract class Command {
    public String name(){
        return this.getClass().getSimpleName();
    }

    public abstract String use();

    public abstract String description();

    public abstract boolean execute(String[] args);
}
