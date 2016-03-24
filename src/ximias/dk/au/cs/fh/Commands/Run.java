package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.ThreadedExechuter;
import ximias.dk.au.cs.fh.Components.Viewer;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by Alex on 14/01/2016.
 */
public class Run extends Command {
    private  static ArrayList<Thread> threads = new ArrayList();
    @Override
    public boolean execute(String[] args) {
        if (args.length<1){
            Viewer.print("Subroutine needs an argument");
        }
        if (Lookup.getSubroutine().contains(args[0])){
            threads.add(new Thread(new ThreadedExechuter(Lookup.getSubroutine().getLinesIn(args[0]))));
            threads.get(threads.size()-1).start();
            return true;
        }
        Viewer.print("The subroutine '"+args[0]+ "' was not found");
        return false;
    }

    private static final ReentrantLock lock = new ReentrantLock();
    public static boolean isDone() {
        lock.lock();
        try {
            Thread.sleep(5);
            removeDeads();
            return threads.size() <= 1;
        } catch (InterruptedException e) {
            return true;
        } finally {
            lock.unlock();
        }
    }

    public static boolean noThreads(){
        return threads.size()<1;
    }

    public static void removeDeads(){
        ArrayList<Thread> temp = new ArrayList<>();
        for (Thread thread:threads) {
            if (thread.isAlive()){
                temp.add(thread);
            }
        }
        threads = temp;
    }
}