package ximias.dk.au.cs.fh.Components;

import ximias.dk.au.cs.fh.Commands.*;

import java.util.ArrayList;

/**
 * Created by Alex on 14/01/2016.
 * Houses thread-specific commands and state variables.
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
    }

    public boolean run(String cmd, String[] args) {
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

    public boolean runCommand(String cmd, String[] args) {
        for (Command command : commands) {
            if (command.name().equalsIgnoreCase(cmd)) {
                return command.execute(args);
            }
        }
        return false;
    }

    @Override
    public String getLastJump() {
        return lastJump;
    }

    @Override
    public void setLastJump(String lastJump) {
        this.lastJump = lastJump;
    }

    @Override
    public boolean getFlowChange() {
        return flowChange;
    }

    @Override
    public void setFlowChange(boolean flowChange) {
        this.flowChange = flowChange;
    }

    @Override
    public void setCurrentLines(ArrayList<String> l) {
        jumpto.setLines(l);
    }

    @Override
    public ArrayList<String> getCurrentLines() {
        return jumpto.getLines();
    }

    @Override
    public String getLastSubroutine() {
        return lastSubroutine;
    }
    public boolean didWait(){
        boolean ans = wait;
        wait = false;
        return ans;
    }
    public void waiting(){
        wait=true;
    }
}
