package ximias.dk.au.cs.fh.Components;

import java.util.ArrayList;

/**
 * Created by Alex on 05/01/2016.
 */
public class Mem {
    ArrayList<MemPair> memory = new ArrayList<MemPair>();

    public void add(MemPair item){
        if (containsKey(item.getKey())){
            remove(item.getKey());
        }
        memory.add(item);
    }

    void remove(String memKey){
        for (MemPair pair: memory){
            if (pair.getKey().equals(memKey)){
                memory.remove(pair);
                return;
            }
        }
    }

    public String getValue(String memKey){
        for (MemPair pair:memory){
            if(pair.getKey().equals(memKey)){
                return(pair.getValue());
            }
        }
        return ("");
    }

    public boolean containsKey(String key){
        for (MemPair pair : memory){
            if (pair.getKey().equals(key)){
                return true;
            }
        }
        return false;
    }

    public static String[] getValuesInArgs(String[] args){
        String[] ans = new String[args.length];
        for (int i=0; i<args.length;i++){
            if (args[i].contains(Constants.VARIABLE_SYMBOL)) {
                ans[i] = Lookup.getMemInstance().getValue(args[i].substring(1));
            }else{
                ans[i] = args[i];
            }
        }
        return ans;
    }
}
