package ximias.dk.au.cs.fh.Components;

import ximias.dk.au.cs.fh.Commands.*;

import java.util.ArrayList;

/**
 * Created by Alex on 14/01/2016.
 * Houses thread-specific commands and variables concerning what state the execution is in.
 */
class ThreadedLookup implements IFlowchange {//TODO: some commands are not thread specific and does not have concurrency issues
    private String lastJump="", lastSubroutine;
    private boolean flowChange = true;
    private boolean wait=false;
    private final ArrayList<Command> commands = new ArrayList<>();
    private final Jumpto jumpto = new Jumpto(this);

    ThreadedLookup(ArrayList<String> l, String subsectionName) {
        this.lastSubroutine=subsectionName;
        setCurrentLines(l);
        flowChange = true;
        commands.add(new Echo());
        commands.add(new Set());
        commands.add(new Add());
        commands.add(new Sub());
        commands.add(new Mul());
        commands.add(new Div());
        commands.add(new Wait(this));
        commands.add(new endsubroutine(this));
        commands.add(jumpto);
        commands.add(Lookup.getSection());
        commands.add(new Run(this));
        commands.add(new If(this));
        commands.add(new Exit(this));
        commands.add(new Input());
        commands.add(new Clear());
        commands.add(new TextColor());
        commands.add(new TextStyle());
        commands.add(new TextSize());
        commands.add(new Execute());
        commands.add(new WindowSize());
        commands.add(new Popup());
        commands.add(new Window());
        commands.add(new Button());
        commands.add(new Label());
        commands.add(new Random());
        commands.add(new Keyboard());
        commands.add(new Image());
        commands.add(new Save());
        commands.add(new Load());
        commands.add(new Log());
        commands.add(new Textarea());
        commands.add(new Draw());
        commands.add(new Buffer());
        commands.add(new Local());
        commands.add(new Inc());
        commands.add(new Dec());
        commands.add(new ClearWindow());
    }

    /**
     * Runs a command returns true if it was successful
     * @see IFlowchange
     * @param cmd the command to be executed
     * @param args the arguments of the command
     * @return true, if the command could run successfully.
     * @throws InterruptedException
     */
    public boolean run(String cmd, String[] args) throws InterruptedException {
        for (Command command : commands) {
            if (command.name().equalsIgnoreCase(cmd)) {
                if (command.execute(args)) {
                    return true;
                } else {
                    Viewer.print("Command could not be executed");
                    return false;
                }
            }
        }
        Viewer.print("Unknown command");
        return false;
    }

    /**
     * Run a single command in this scope and thread.
     * @see IFlowchange
     * @param cmd the command to run.
     * @param args the arguments of the command.
     * @return whether the command could execute.
     */
    public boolean runCommand(String cmd, String[] args) {
        for (Command command : commands) {
            if (command.name().equalsIgnoreCase(cmd)) {
                try {
                    return command.execute(args);
                } catch (InterruptedException ignored) {}
            }
        }
        return false;
    }

    /**
     * Returns the name of the last jumppoint.
     * @see IFlowchange
     * @return name of the last point jumped to.
     */
    @Override
    public String getLastJump() {
        return lastJump;
    }

    /**
     * Setter for last jump point
     * @see IFlowchange
     * @param lastJump The name of the point jumped to.
     */
    @Override
    public void setLastJump(String lastJump) {
        this.lastJump = lastJump;
    }

    /**
     * Returns whether a change in flow has occurred.
     * @see IFlowchange
     * @return true if a change in program flow has happened.
     */
    @Override
    public boolean getFlowChange() {
        return flowChange;
    }

    /**
     * setter for the variable flowChange. Should be set to true, if a change in program flow has occured.
     * @see IFlowchange
     * @param flowChange true if a change in flow has occurred, false if it has been handled.
     */
    @Override
    public void setFlowChange(boolean flowChange) {
        this.flowChange = flowChange;
    }

    /**
     * Set the current lines to be executed.
     * @see IFlowchange
     * @param l the list of the new lines to execute
     */
    @Override
    public void setCurrentLines(ArrayList<String> l) {
        jumpto.setLines(l);
    }

    /**
     * Getter for the current lines to be executed
     * @see IFlowchange
     * @return the list of lines to be executed.
     */
    @Override
    public ArrayList<String> getCurrentLines() {
        return jumpto.getLines();
    }

    /**
     * Getter for last started subroutine.
     * @see IFlowchange
     * @return the name of the last subroutine.
     */
    @Override
    public String getLastSubroutine() {
        return lastSubroutine;
    }

    /**
     * Returns whether the program has slept.
     * @see IFlowchange
     * @return true, if the Thread has slept since last call
     */
    public boolean didWait(){
        boolean ans = wait;
        wait = false;
        return ans;
    }

    /**
     * Notifier of the thread having slept.
     * @see IFlowchange
     */
    public void waiting(){
        wait=true;
    }
}
