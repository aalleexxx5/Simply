package ximias.dk.au.cs.fh.Commands;

import java.util.ArrayList;

/**
 * Created by Alex on 05/01/2016.
 * Command. Defines a destination for Goto.. I mean JumpTo
 */
public class Section extends Command {
    private final ArrayList<SectionPair> sections = new ArrayList<>();
    public Section(ArrayList<String> lines){
        int i = 0;
        for(String line:lines){
            if(line.toLowerCase().startsWith("section")){
                sections.add(new SectionPair(line.substring(8), new ArrayList<>(lines.subList(i + 1, lines.size()))));
            }
            i++;
        }
    }

    @Override
    public String description() {
        return "declare the start of a section";
    }

    @Override
    public String use(){
        return "section <name>";
    }

    @Override
    public boolean execute(String[] args) {
        return true;
    }

    public boolean contains(String key){
        for(SectionPair section : sections){
            if (section.getKey().equals(key)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getLinesAt(String key){
        for (SectionPair section : sections){
            if(section.getKey().equals(key)){
                return(section.getValue());
            }
        }
        return null;
    }
}

 class SectionPair {
    private final String key;
    private final ArrayList<String> value;
    public SectionPair(String key, ArrayList<String> value){
        this.key=key;
        this.value=value;
    }
    public String getKey(){
        return key;
    }

    public ArrayList<String> getValue(){
        return value;
    }
}