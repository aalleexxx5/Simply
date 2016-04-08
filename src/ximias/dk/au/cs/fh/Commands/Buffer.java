package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Commands.Graphics.Graphics;

/**
 * Created by Alex on 04/04/2016.
 * Command to buffer a graphics element into memory
 */
public class Buffer extends Command {
    @Override
    public String use() {
        return "buffer <name>";
    }

    @Override
    public String description() {
        return "Buffer graphics, to display at a location later. Subsequent draws will use the buffered version";
    }

    @Override
    public boolean execute(String[] args) {
        return Graphics.buffer(args[0]);
    }
}
