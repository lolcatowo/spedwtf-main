package me.alpha432.oyvey.features.command.commands;

import me.alpha432.oyvey.Sped;
import me.alpha432.oyvey.features.command.Command;

public class UnloadCommand
        extends Command {
    public UnloadCommand() {
        super("unload", new String[0]);
    }

    @Override
    public void execute(String[] commands) {
        Sped.unload(true);
    }
}

