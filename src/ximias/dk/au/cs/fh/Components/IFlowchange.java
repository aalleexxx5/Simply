package ximias.dk.au.cs.fh.Components;

import java.util.ArrayList;

/**
 * Created by Alex on 14/01/2016.
 * Interface for Lookup and ThreadedLookup Allows polymorphism in commands.
 */
public interface IFlowchange {
    boolean run(String cmd,String[] args);
    boolean getFlowChange();
    void setFlowChange(boolean f);
    void setCurrentLines(ArrayList<String> l);
    ArrayList<String> getCurrentLines();
    boolean runCommand(String Cmd, String[] args);
    void setLastJump(String name);
    String getLastJump();
}
