package ximias.dk.au.cs.fh.Components;

import java.util.ArrayList;

/**
 * Created by Alex on 14/01/2016.
 * Interface for allowing polymorphism for commands to receive thread specific state variables.
 */
public interface IFlowchange {
    boolean run(String cmd,String[] args) throws InterruptedException;
    boolean getFlowChange();
    void setFlowChange(boolean f);
    void setCurrentLines(ArrayList<String> l);
    ArrayList<String> getCurrentLines();
    boolean runCommand(String Cmd, String[] args);
    void setLastJump(String name);
    String getLastJump();
    String getLastSubroutine();
    boolean didWait();
    void waiting();
}
