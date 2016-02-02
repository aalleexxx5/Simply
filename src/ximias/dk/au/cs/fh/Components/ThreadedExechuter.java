package ximias.dk.au.cs.fh.Components;

import java.util.ArrayList;

/**
 * Created by Alex on 14/01/2016.
 */
public class ThreadedExechuter implements Runnable {
    private ArrayList<String> lines;
    public ThreadedExechuter(ArrayList<String> lines){
        this.lines = lines;
    }
    @Override
    public void run() {
        ThreadedLookup lookup = new ThreadedLookup(lines);
        int i = 1;
        Viewer.print("running Thread ");
        while (lookup.getFlowChange()) {
            lines = lookup.getCurrentLines();
            lookup.setFlowChange(false);
            for (String line : lines) {
                ArrayList<String> arg = new ArrayList<String>();
                String cmd;
                if (line.contains(" ")) {
                    cmd = line.substring(0, line.indexOf(" "));
                    line = line.substring(line.indexOf(" ") + 1);
                }else if (line.length()>1){
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
                    if (lookup.run(cmd, arg.toArray(new String[arg.size()]))) {
                        i++;
                        if (lookup.getFlowChange()) {//If a command changed the lines
                            break;
                        }
                    } else {
                        Viewer.print("An error occurred " + cmd);
                        break;
                    }
                }
            }
        }
        Viewer.doneExecution();
    }
}