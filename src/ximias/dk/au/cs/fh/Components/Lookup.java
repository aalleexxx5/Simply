package ximias.dk.au.cs.fh.Components;

import ximias.dk.au.cs.fh.Commands.*;

import java.util.ArrayList;

/**
 * Created by Alex on 05/01/2016.
 */
public class Lookup implements IFlowchange{
    private static ArrayList<String> lines;
    private static Mem memInstance = new Mem();
    private static Section section;
    private static Subroutine subroutine;
    private static ArrayList<Command> commands = new ArrayList<>();
    private boolean flowChange = true;
    private Jumpto jumpto = new Jumpto(this);
    public Lookup(ArrayList<String> l){
        flowChange = true;
        lines = l;
        section = new Section(l);
        subroutine = new Subroutine(l, this);
        commands.add(new Echo());
        commands.add(new Set());
        commands.add(new Add());
        commands.add(new Sub());
        commands.add(new Mul());
        commands.add(new Div());
        commands.add(new Wait());
        commands.add(section);
        commands.add(subroutine);
        commands.add(new endsubroutine(this));
        commands.add(jumpto);
        commands.add(new Run());
        commands.add(new If(this));
        commands.add(new Exit(this));
        commands.add(new Input());
        commands.add(new Clear());
        commands.add(new TextColor());
        commands.add(new TextSize());
        commands.add(new TextStyle());
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

    public static boolean runMainCommand(String cmd, String[] args){
        for (Command command : commands){
            if(command.name().equalsIgnoreCase(cmd)){
                return command.execute(args);
            }
        }
        return false;
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