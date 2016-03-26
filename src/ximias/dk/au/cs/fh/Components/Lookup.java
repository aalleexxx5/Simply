package ximias.dk.au.cs.fh.Components;

import ximias.dk.au.cs.fh.Commands.*;

import java.util.ArrayList;

/**
 * Created by Alex on 05/01/2016.
 * Keeps track of everything. EVERYTHING! (except symbols, that is Constants's job)
 */
public class Lookup implements IFlowchange{
    private static ArrayList<String> lines;
    private static final Mem memInstance = new Mem();
    private static Section section;
    private static Subroutine subroutine;
    private static final ArrayList<Command> commands = new ArrayList<>();
    private boolean flowChange = true;
    private final Jumpto jumpto = new Jumpto(this);
    public Lookup(ArrayList<String> l){
        flowChange = true;
        lines = l;
        section = new Section(l);
        subroutine = new Subroutine(l);
        commands.add(new Echo());
        commands.add(new Set());
        commands.add(jumpto);
        commands.add(new If(this));
        commands.add(new Add());
        commands.add(new Sub());
        commands.add(new Mul());
        commands.add(new Div());
        commands.add(new Wait());
        commands.add(section);
        commands.add(subroutine);
        commands.add(new endsubroutine(this));
        commands.add(new Clear());
        commands.add(new Run());
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
        setCurrentLines(lines);
    }

    public boolean run(String cmd, String[] args){
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

    public boolean runCommand(String cmd, String[] args){
        for (Command command : commands){
            if(command.name().equalsIgnoreCase(cmd)){
                return command.execute(args);
            }
        }
        return false;
    }

    public static boolean runMainCommand(@SuppressWarnings("SameParameterValue") String cmd, String[] args){
        for (Command command : commands){
            if(command.name().equalsIgnoreCase(cmd)){
                return command.execute(args);
            }
        }
        return false;
    }

    public static int getNumCommands(){
        return commands.size();
    }

    public static String getCommandName(int index){
        return commands.get(index).name();
    }

    public static String getCommandUse(int index){
        return commands.get(index).use();
    }

    public static String getCommandDescription(int index){
        return commands.get(index).description();
    }

    public static Mem getMemInstance(){
        return memInstance;
    }

    public void setFlowChange(boolean b){
        flowChange = b;
    }

    public boolean getFlowChange(){
        return flowChange;
    }

    public static Section getSection() {
        return section;
    }

    public static Subroutine getSubroutine() {
        return subroutine;
    }

    public void setCurrentLines(ArrayList<String> lines) {
        jumpto.setLines(lines);
    }

    @Override
    public ArrayList<String> getCurrentLines(){
        return jumpto.getLines();
    }

    public static void setLines(ArrayList<String> lines) {
        Lookup.lines = lines;
    }

    public static ArrayList<String> getLines (){
        return lines;
    }
}