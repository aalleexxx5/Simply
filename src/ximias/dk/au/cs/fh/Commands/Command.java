package ximias.dk.au.cs.fh.Commands;

/**
 * Created by Alex on 05/01/2016.
 * Abstract class, must be implemented be all commands
 */
public abstract class Command {
    public String name(){
        return this.getClass().getSimpleName();
    }

    /**
     * The usages of a command, Usually its name, followed by the arguments it takes in the right order.<br/>
     * This may be a multi-line string.
     * @return <b>String</b> usage
     */
    public abstract String use();

    /**
     * A short one-line description of the command, usually explaining what purpose it would be used for.<br/>
     * This should be a concise single-line string.
     * @return <b>String</b> description
     */
    public abstract String description();

    /**
     * Called when executing the command
     * @param args the arguments the command receives
     * @return <b>boolean</b weather the command was executed correctly. <br/> If false, the program returns an error and terminates the thread calling this command.
     */
    public abstract boolean execute(String[] args);
}
