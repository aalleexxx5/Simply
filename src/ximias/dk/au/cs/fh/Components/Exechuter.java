package ximias.dk.au.cs.fh.Components;

import java.util.ArrayList;

/**
 * Created by Alex on 10/01/2016.
 * Runs the Code line-by-line with a lot of help from Lookup
 */
class Exechuter implements Runnable{
    private final IFlowchange lookup;
    public Exechuter(Lookup lookup){
        this.lookup = lookup;
    }

    public void run() {
        ArrayList<String> lines;
        int i;
        Viewer.print("running Code... ");
        while (lookup.getFlowChange()) {
            i=1;
            lines = lookup.getCurrentLines();
            lookup.setFlowChange(false);
            for (String line : lines) {
                ArrayList<String> arg = new ArrayList<>();
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
                if(!cmd.equals("skip")){
                    if (lookup.run(cmd, arg.toArray(new String[arg.size()]))) {
                        i++;
                        if (lookup.getFlowChange()) {//If a command changed the lines
                            break;
                        }
                    } else {
                        if (lookup.getLastJump() == null) {
                            Viewer.print("An error occurred in line " + i + " :" + cmd);
                        }else {
                            Viewer.print("An error occurred "+i+" lines after the section " + lookup.getLastJump() + "\nat the command " + cmd);
                        }
                        break;
                    }
                }

            }
        }
        Viewer.doneExecution();
    }
}
