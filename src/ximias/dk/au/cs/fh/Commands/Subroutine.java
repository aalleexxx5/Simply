package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Lookup;

import java.util.ArrayList;

/**
 * Created by Alex on 05/01/2016.
 * Command. This is the closest you get to a function.
 */
public class Subroutine extends Command {
    private final ArrayList<SectionPair> routines = new ArrayList<>();
    public Subroutine(ArrayList<String> lines){
        //noinspection unchecked
        ArrayList<String> newlines = (ArrayList<String>) lines.clone();
        int l=0;
        for(int i = 0; i<lines.size();i++){
            if(lines.get(i).toLowerCase().startsWith("subroutine")){ //remove the lines added to the subroutine
                for(int j = i; j<lines.size();j++){
                    if (lines.get(j).toLowerCase().startsWith("endsubroutine")){
                        routines.add(new SectionPair(lines.get(i).substring(11), new ArrayList<>(lines.subList(i+1, j+1))));
                        for(int k=i; k<=j;k++){
                            newlines.remove(i-l);
                        }
                        l+=1+j-i;
                        i=j;
                        break;
                    }
                }
            }
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
    public String description() {
        return "begins a subroutine. A subroutine must end with endsubroutine";
    }

    @Override
    public String use(){
        return "subroutine <name>";
    }

    @Override
    public boolean execute(String[] args) {
        return true;
    }
}
