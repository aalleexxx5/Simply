package ximias.dk.au.cs.fh.Components;

import ximias.dk.au.cs.fh.Commands.*;

import java.util.ArrayList;

/**
 * Created by Alex on 14/01/2016.
 */
public class ThreadedLookup implements IFlowchange {
        private boolean flowChange = true;
        private ArrayList<Command> commands = new ArrayList<Command>();
        private Jumpto jumpto = new Jumpto(this);
        public ThreadedLookup(ArrayList<String> l){
            setCurrentLines(l);
            flowChange = true;
            commands.add(new Echo());
            commands.add(new Set());
            commands.add(new Add());
            commands.add(new Sub());
            commands.add(new Mul());
            commands.add(new Div());
            commands.add(new Wait());
            commands.add(new endsubroutine(this));
            commands.add(jumpto);
            commands.add(Lookup.getSection());
            commands.add(new Run());
            commands.add(new If(this));
            commands.add(new Exit(this));
            commands.add(new Input());
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
}
