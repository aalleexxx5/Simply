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

    private boolean hasLocal(String key){
        lock.lock();
        try {
            for (MemPair pair : local) {
                if (pair.getKey().equals(Thread.currentThread().getName()+key)){
                    return true;
                }
            }
            return false;
        }finally {
            lock.unlock();
        }
    }

    public static void removeAll(){
        memory = new ArrayList<>();
        local = new ArrayList<>();
    }

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
