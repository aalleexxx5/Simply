package ximias.dk.au.cs.fh.Components;

import java.util.ArrayList;

/**
 * Created by Alex on 14/01/2016.
 * Interface for allowing polymorphism for commands to receive thread specific state variables.
 */
public interface IFlowchange {
    /**
     * Execute a command
     * @param cmd the command to be executed
     * @param args the arguments of the command
     * @return weather the operation was successful, if false, the program should display an error and terminate.
     * @throws InterruptedException if the operation could not complete
     */
    boolean run(String cmd,String[] args) throws InterruptedException;

    /**
     * To check for a change in program flow
     * @return Weather a change in program flow has occurred.
     */
    boolean getFlowChange();

    /**
     * To notify of a change in program flow
     * @param f Weather the change has been handled.
     */
    void setFlowChange(boolean f);

    /**
     * Changes the current list of lines to be executed.
     * Use setFlowChange to notify of this change.
     * @param l the list of the new lines to execute
     */
    void setCurrentLines(ArrayList<String> l);

    /**
     * returns the current list of lines to be executed. Will update in case of a subroutine getting called <br/>
     * Use in conjunction with get and setFlowChange to see if this has updated.
     * @return the current list of lines to be executed
     */
    ArrayList<String> getCurrentLines();

    /**
     * run a single command, is used when a command calls another.
     * @param Cmd the command to run.
     * @param args the arguments of the command.
     * @return weather the command was successfull.
     */
    boolean runCommand(String Cmd, String[] args);

    /**
     * Set the last jump point, used to display section names in error messages
     * @param name the name of the section jumped to
     */
    void setLastJump(String name);

    /**
     * Getter for last jump point, returns the name of the current section.
     * @return String wich is the name of the section last jumped to.
     */
    String getLastJump();

    /**
     * Used to to validate that a subroutine does not call or run itself, as that would potentially create a chain-reaction.
     * @return the name of the last run subroutine
     */
    String getLastSubroutine();

    /**
     * Used to check if the thread has slept since last time this was called. Used to check for infinite loops.
     * @return weather the thread has slept since last call to this.
     */
    boolean didWait();

    /**
     * Called before Thread.sleep, to allow for infinite loops when thread sleeps intermittently.
     */
    void waiting();
}
