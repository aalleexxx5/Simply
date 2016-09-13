package ximias.dk.au.cs.fh.Components;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Alex on 05/01/2016.
 * Keeps track of all variables used in the application.
 */
public class Mem {
    private static ArrayList<MemPair> memory = new ArrayList<>();
    private static ArrayList<MemPair> local = new ArrayList<>();
    private static final ReentrantLock lock = new ReentrantLock();
    public static void add(MemPair item){
        lock.lock();
        try {
            if (containsLocalKey(item.getKey())){
                addLocal(item);
                return;
            }
            if (containsKey(item.getKey())) {
                remove(item.getKey());
            }
            memory.add(item);
        }finally {
            lock.unlock();
        }
    }

    /**
     * Adds a global variable.
     * @param item the memPair object to store.
     */
    public static void addGlobal(MemPair item){
        lock.lock();
        try {
            if (containsKey(item.getKey())) {
                remove(item.getKey());
            }
            memory.add(item);
        }finally {
            lock.unlock();
        }
    }

    /**
     * Removes a variable.
     * @param memKey the key of the variable to be removed.
     */
    private static void remove(String memKey){
        lock.lock();
        try {
            for (MemPair pair : memory) {
                if (pair.getKey().equals(memKey)) {
                    memory.remove(pair);
                    return;
                }
            }
        }finally {
            lock.unlock();
        }
    }

    /**
     * Add a local variable.
     * @param item the memPair object to store.
     */
    public static void addLocal(MemPair item){
        lock.lock();
        try {
            if (containsLocalKey(item.getKey())) {
                removeLocal(item.getKey());
            }
            local.add(new MemPair(Thread.currentThread().getName()+item.getKey(),item.getValue()));
        }finally {
            lock.unlock();
        }
    }

    /**
     * Remove a local variable.
     * @param memKey the key of the variable to remove.
     */
    private static void removeLocal(String memKey){
        lock.lock();
        try {
            for (MemPair pair : local) {
                if (pair.getKey().equals(Thread.currentThread().getName()+memKey)) {
                    local.remove(pair);
                    return;
                }
            }
        }finally {
            lock.unlock();
        }
    }

    /**
     * Remove all local variables. Used when exiting local scope.
     */
    public static void removeLocals(){
        lock.lock();
        try {
            for (int i = 0; i < local.size(); i++) {
                if (local.get(i).getKey().startsWith(Thread.currentThread().getName())){
                    local.remove(local.get(i));
                    i--;
                }
            }
        }finally {
            lock.unlock();
        }
    }

    /**
     * Removes all variables, called on a memory reset.
     */
    public static void removeAll(){
        memory = new ArrayList<>();
        local = new ArrayList<>();
    }

    /**
     * Returns the value associated with a key in a memory pair with global scope.
     * @param memKey The name of the variable.
     * @return The value of the variable with the given name.
     * Empty string if no variable exists with the given name.
     */
    private static String getValue(String memKey){
        lock.lock();
        try {
            String ret = getLocalValue(Thread.currentThread().getName()+memKey);
            if (!ret.equals("")) return ret;
            for (MemPair pair : memory) {
                if (pair.getKey().equals(memKey)) {
                    return (pair.getValue());
                }
            }
            return ("");
        }finally {
            lock.unlock();
        }
    }

    /**
     * Returns the value associated with a key in a memory pair with local scope.
     * @param memKey The name of the variable.
     * @return The value of the variable with the given name.
     * Empty string if no variable exists with the given name.
     */
    private static String getLocalValue(String memKey){
        lock.lock();
        try {
            for (MemPair pair : local) {
                if (pair.getKey().equals(memKey)) {
                    return (pair.getValue());
                }
            }
            return ("");
        }finally {
            lock.unlock();
        }
    }

    /**
     * Returns whether a local variable with the given key exists.
     * @param key the name of the variable to search for.
     * @return true, if a variable with the given key exists. False in all other cases.
     */
    private static boolean containsLocalKey(String key){
        lock.lock();
        try {
            for (MemPair pair : local) {
                if (pair.getKey().equals(Thread.currentThread().getName()+key)) {
                    return true;
                }
            }
            return false;
        }finally {
            lock.unlock();
        }
    }

    /**
     * Returns whether a global variable with the given key exists.
     * @param key the name of the variable to search for.
     * @return true, if a variable with the given key exists, False in all other cases.
     */
    private static boolean containsKey(String key){
        lock.lock();
        try {
            for (MemPair pair : memory) {
                if (pair.getKey().equals(key)) {
                    return true;
                }
            }
            return false;
        }finally {
            lock.unlock();
        }
    }

    /**
     * Utility function for converting a string array containing a mix of values and variable names, to only values.
     * @param args arguments which may contain variables.
     * @return arguments with all variable names converted to values.
     */
    public static String[] getValuesInArgs(String[] args){
        String[] ans = new String[args.length];
        for (int i=0; i<args.length;i++){
            while (args[i].contains(Constants.VARIABLE_SYMBOL)) {
                args[i] = args[i].substring(0,args[i].lastIndexOf(Constants.VARIABLE_SYMBOL))+getValue(args[i].substring(args[i].lastIndexOf(Constants.VARIABLE_SYMBOL)+1));
            }
            ans[i] = args[i];
        }
        return ans;
    }
}
