package ximias.dk.au.cs.fh.Components;

import java.util.ArrayList;

/**
 * Created by Alex on 14/01/2016.
 */
public interface IFlowchange {
    boolean getFlowChange();
    void setFlowChange(boolean f);
    void setCurrentLines(ArrayList<String> l);
    ArrayList<String> getCurrentLines();
    boolean runCommand(String Cmd, String[] args);
}
