package ximias.dk.au.cs.fh.Commands;

import ximias.dk.au.cs.fh.Components.*;

/**
 * Created by Alex on 05/01/2016.
 */
public class Mul extends Command {
    @Override
    public boolean execute(String[] args) {
        args = Mem.getValuesInArgs(args);
        try {
            if (Numbers.isNumber(args[0])) {
                int ans = Integer.valueOf(args[0]) * Integer.valueOf(args[1]);
                if (args.length > 2) {
                    for (int i = 2; i < args.length; i++) {
                        ans *= Integer.valueOf(args[i]);
                    }
                }
                Viewer.print(String.valueOf(ans));
                return true;
            } else {
                int ans = Integer.valueOf(args[1]) * Integer.valueOf(args[2]);
                if (args.length > 2) {
                    for (int i = 3; i < args.length; i++) {
                        ans *= Integer.valueOf(args[i]);
                    }
                }
                Lookup.getMemInstance().add(new MemPair(args[0], String.valueOf(ans)));
                return true;
            }
        }catch (NumberFormatException e){
            return false;
        }
    }
}
