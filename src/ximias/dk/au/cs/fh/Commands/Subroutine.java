package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Commands.Graphics.Graphics;
import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.Viewer;

import java.util.ArrayList;

/**
 * Created by Alex on 05/01/2016.
 * Command. This is the closest you get to a function.
 * TODO: Nesting, add a no endsubroutine error, Move initialisation to a different class
 */
public class Subroutine extends Command {
    private final ArrayList<SectionPair> routines = new ArrayList<>();
    Lookup lookup;
    public Subroutine(ArrayList<String> lines,Lookup lookup){
        //noinspection unchecked
        this.lookup=lookup;
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
            }else if (lines.get(i).startsWith("comment")){
                for (int j = i; j < lines.size(); j++) {
                    if (lines.get(j).toLowerCase().startsWith("endcomment")){
                        for (int k = i; k <=j; k++) {
                            newlines.remove(i-l);
                        }
                        l+=1+j-i;
                        i=j;
                        break;
                    }
                }
            }else if (lines.get(i).toLowerCase().startsWith("graphics")){
                for(int j = i; j<lines.size();j++){
                    if (lines.get(j).toLowerCase().startsWith("endgraphics")){
                        String[] params = lines.get(i).split(" ");
                        if (params.length<4 || !ArgManipulation.isNumberAndPositive(params[2])||!ArgManipulation.isNumberAndPositive(params[3])){
                            Viewer.print("Graphics needs name and size as arguments");
                        }else{
                            Graphics.add(params[1],Integer.valueOf(params[2]),Integer.valueOf(params[3]),lines.subList(i+1, j+1).toArray(new String[0]));
                        }
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
        lookup.setFlowChange(true);
        lookup.setCurrentLines(newlines);
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

    ArrayList<String> getLinesIn(String key){
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
