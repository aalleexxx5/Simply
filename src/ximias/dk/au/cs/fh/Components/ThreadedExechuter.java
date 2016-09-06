package ximias.dk.au.cs.fh.Components;

import java.util.ArrayList;

/**
 * Created by Alex on 14/01/2016.
 * Executes code in a separate thread. this is created by the Run command (and execute, but don't tell anyone)
 */
public class ThreadedExechuter implements Runnable {
    private ArrayList<String> lines;
    private final String subsectionName;
    public ThreadedExechuter(ArrayList<String> lines,String section){
        this.lines = lines;
        this.subsectionName = section;
    }
    @Override
    public void run() {
        IFlowchange lookup = new ThreadedLookup(lines,subsectionName);
        //System.out.println("running Thread ");
        while (lookup.getFlowChange()) {
            int i = 1;
            lines = lookup.getCurrentLines();
            lookup.setFlowChange(false);
            for (String line : lines) {
                ArrayList<String> arg = new ArrayList<>();
                String cmd;
                if (line.contains(" ")) {
                    cmd = line.substring(0, line.indexOf(" "));
                    line = line.substring(line.indexOf(" ") + 1);
                }else if (line.length()>=1){
                    cmd=line;
                    line = "";
                }else{
                    cmd="skip";
                    line="";
                }
                while (!line.isEmpty()) {
                    if (line.contains(" ")) {
                        arg.add(line.substring(0, line.indexOf(" ")));
                        line = line.substring(line.indexOf(" ") + 1);
                    } else {
                        arg.add(line);
                        line = "";
                    }
                }
                if(!cmd.equals("skip")) {
                    try {
                        if (lookup.run(cmd, arg.toArray(new String[arg.size()]))) {
                            i++;
                            if (lookup.getFlowChange()) {//If a command changed the lines
                                break;
                            }
                        } else {
                            if (lookup.getLastJump() == null) {
                                Viewer.print("An error occurred in subroutine: "+subsectionName+ " Line "+i +": "+ cmd);
                            }else {
                                Viewer.print("An error occurred in subroutine: " + subsectionName + "\nIn Section: " + lookup.getLastJump() + ", Line " + i + ": " + cmd);
                            }
                            break;
                        }
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
        Mem.removeLocals();
        Viewer.doneExecution();
    }
}