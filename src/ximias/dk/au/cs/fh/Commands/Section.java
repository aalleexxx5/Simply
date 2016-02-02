package ximias.dk.au.cs.fh.Commands;

import java.util.ArrayList;

/**
 * Created by Alex on 05/01/2016.
 */
public class Section extends Command {
    ArrayList<SectionPair> sections = new ArrayList<SectionPair>();
    public Section(ArrayList<String> lines){
        int i = 0;
        for(String line:lines){
            if(line.toLowerCase().startsWith("section")){
                sections.add(new SectionPair(line.substring(8),new ArrayList<String>(lines.subList(i+1, lines.size()))));
            }
            i++;
        }
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
    private String key;
    private ArrayList<String> value;
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