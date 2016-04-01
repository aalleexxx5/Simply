package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.ThreadedExechuter;
import ximias.dk.au.cs.fh.Components.Viewer;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


/**
 * Created by Alex on 14/01/2016.
 * Command. Runs a subroutine in a new thread.
 * you thought multithreading was hard? try multithreading in the same function, sharing the same variables Concurrency issues, here I come!
 */
public class Run extends Command {
    private  static ArrayList<Thread> threads = new ArrayList<>();

    @Override
    public String description() {
        return "runs a subroutine as a new task. This allows for multitasking";
    }

    @Override
    public String use(){
        return "run <subroutine>";
    }

    @Override
    public boolean execute(String[] args) {//TODO: prevent cascading threads
        if (args.length<1){
            Viewer.print("Subroutine needs an argument");
        }
        if (Lookup.getSubroutine().contains(args[0])){
            threads.add(new Thread(new ThreadedExechuter(Lookup.getSubroutine().getLinesIn(args[0]),args[0])));
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

    private static void removeDeads(){
        threads = threads.stream().filter(Thread::isAlive).collect(Collectors.toCollection(ArrayList::new));
    }
}