package ximias.dk.au.cs.fh.Components;

import ximias.dk.au.cs.fh.Commands.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Alex on 05/01/2016.
 * Keeps track of everything. EVERYTHING!
 * Houses commands and state variables. State variables might be moved for cleaner code
 */
public class Lookup implements IFlowchange{
    private boolean wait=false;
    private String lastJump="";
    private static ArrayList<String> lines;
    private static final Mem memInstance = new Mem();
    private static Section section;
    private static Subroutine subroutine;
    private static final ArrayList<Command> commands = new ArrayList<>();
    private boolean flowChange = true;
    private final Jumpto jumpto = new Jumpto(this);
    public Lookup(ArrayList<String> l){
        lines = l;
        section = new Section(l);
        subroutine = new Subroutine(l,this);
        while(!commands.isEmpty()){
            commands.remove(0);
        }
        commands.add(new Echo());
        commands.add(new Set());
        commands.add(jumpto);
        commands.add(new If(this));
        commands.add(new Add());
        commands.add(new Sub());
        commands.add(new Mul());
        commands.add(new Div());
        commands.add(new Wait(this));
        commands.add(section);
        commands.add(subroutine);
        commands.add(new endsubroutine(this));
        commands.add(new Clear());
        commands.add(new Run(this));
        commands.add(new Input());
        commands.add(new Exit(this));
        commands.add(new TextColor());
        commands.add(new TextSize());
        commands.add(new TextStyle());
        commands.add(new WindowSize());
        commands.add(new Execute());
        commands.add(new Popup());
        commands.add(new Dialog());
        commands.add(new Window());
        commands.add(new Button());
        commands.add(new Label());
        commands.add(new Pane());
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
     * Runs a command.
     * @see IFlowchange
     * @param cmd the command to be executed
     * @param args the arguments of the command
     * @return Weather the operation was successful
     * @throws InterruptedException If the operation could not complete, but no error occurred.
     */
    public boolean run(String cmd, String[] args) throws InterruptedException {
        for (Command command : commands){
            if (command.name().equalsIgnoreCase(cmd)){
                if (command.execute(args)) {
                    return true;
                }else{
                    Viewer.print("Command could not be executed");
                    return false;
                }
            }
        }
        Viewer.print("Unknown command");
        return false;
    }

    /**
     * Runs a command in this thread. Mostly obsolete as this is main thread and the static runMainCommand exists.
     * @see IFlowchange
     * @param cmd The command to run.
     * @param args the arguments of the command.
     * @return whether the command ran successfully.
     */
    public boolean runCommand(String cmd, String[] args){
        return runMainCommand(cmd, args);
    }

    /**
     * Runs a command as if it was on the main thread.
     * @param cmd the command to run.
     * @param args the arguments for the command.
     * @return whether the command executed successfully.
     */
    public static boolean runMainCommand(@SuppressWarnings("SameParameterValue") String cmd, String[] args){
        for (Command command : commands){
            if(command.name().equalsIgnoreCase(cmd)){
                try {
                    return command.execute(args);
                } catch (InterruptedException ignored) {}
            }
        }
        return false;
    }

    /**
     * The last section jumped to, used in error messages.
     * @see IFlowchange
     * @return the name of the last section jumped to.
     */
    @Override
    public String getLastJump() {
        return lastJump;
    }

    /**
     * Setter for last jump.
     * @see IFlowchange
     * @param lastJump the section last jumped to.
     */
    @Override
    public void setLastJump(String lastJump) {
        this.lastJump = lastJump;
    }

    /**
     * Returns the number of commands, used for creating the command list.
     * @return the number of commands.
     */
    static int getNumCommands(){
        return commands.size();
    }

    /**
     * Returns the name of a command at a given index.
     * @param index a positive integer, no larger than NumCommands.
     * @return The name of the command at the index.
     */
    static String getCommandName(int index){
        return commands.get(index).name();
    }

    /**
     * returns the use of the command at a given index.
     * @param index a positive integer no larger than NumCommands.
     * @return the usage of the command at the given index.
     */
    static String getCommandUse(int index){
        return commands.get(index).use();
    }

    /**
     * returns the description of a command at a given index.
     * @param index a positive integer no larger than NumCommands.
     * @return the description of the command at the given index.
     */
    static String getCommandDescription(int index){
        return commands.get(index).description();
    }

    /**
     * Used to get the instance of Mem.
     * @return the mem instance.
     */
    public static Mem getMemInstance(){
        return memInstance;
    }

    /**
     * @see IFlowchange
     * @param b whether a change in program flow has happened
     */
    public void setFlowChange(boolean b){
        flowChange = b;
    }

    /**
     * @see IFlowchange
     * @return whether a change in program flow has happened.
     */
    public boolean getFlowChange(){
        return flowChange;
    }

    /**
     * returns the name of the last section jumped to.
     * @see IFlowchange
     * @return the name of the last section jumped to.
     */
    public static Section getSection() {
        return section;
    }

    /** Used to get the name of the last run subroutine.
     * @see IFlowchange
     * @return the name of the last subroutine run.
     */
    public static Subroutine getSubroutine() {
        return subroutine;
    }

    /**
     * Setter for the current lines to be executed.
     * @see IFlowchange
     * @param lines the lines to be executed.
     */
    public void setCurrentLines(ArrayList<String> lines) {
        jumpto.setLines(lines);
    }

    /**
     * Getter for the lines to be executed.
     * @see IFlowchange
     * @return the lines to be executed.
     */
    @Override
    public ArrayList<String> getCurrentLines(){
        return jumpto.getLines();
    }

    /**
     * Setter for the lines of the entire program. Not used at program execution.
     * @param lines the lines of the file to run.
     */
    public static void setLines(ArrayList<String> lines) {
        Lookup.lines = lines;
    }

    /**
     * Getter for the lines of the entire program.
     * @return the lines of the entire program.
     */
    public static ArrayList<String> getLines (){
        return lines;
    }

    /**
     * Returns the name of the last run subroutine.
     * @see IFlowchange
     * @return the name of the last run subroutine.
     */
    public String getLastSubroutine() {
        return "";
    }

    /**
     * Whether the thread has slept since last time this was called.
     * @see IFlowchange
     * @return true if the thread has slept since last call.
     */
    public boolean didWait(){
        boolean ans = wait;
        wait = false;
        return ans;
    }

    /**
     * called before the Thread sleeps.
     */
    public void waiting(){
        wait=true;
    }
}