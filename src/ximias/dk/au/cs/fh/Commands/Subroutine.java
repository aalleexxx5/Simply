package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Lookup;

import java.util.ArrayList;

/**
 * Created by Alex on 05/01/2016.
 */
public class Subroutine extends Command {
    ArrayList<SectionPair> routines = new ArrayList<SectionPair>();
    public Subroutine(ArrayList<String> lines,Lookup lookup){
        int i = 0;
        ArrayList<String> newlines = (ArrayList<String>) lines.clone();
        for(String line:lines){
            if(line.toLowerCase().startsWith("subroutine")){ //remove the lines added to the subroutine
                for(int j = i; j<lines.size();j++){
                    if (lines.get(j).toLowerCase().startsWith("endsubroutine")){
                        routines.add(new SectionPair(line.substring(11), new ArrayList<String>(lines.subList(i+1, j+1))));
                        for(int k=i; k<=j;k++){
                            newlines.remove(i);
                        }
                        break;
                    }
                }
            }
            i++;
        }
        Lookup.setLines(newlines);
    }

    public boolean contains(String key){
        for(SectionPair routine : routines){
            if (routine.getKey().equals(key)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getLinesIn(String key){
        for (SectionPair routine : routines){
            if(routine.getKey().equals(key)){
                return(routine.getValue());
            }
        }
        return null;
    }

    @Override
    public boolean execute(String[] args) {
        return true;
    }
}
