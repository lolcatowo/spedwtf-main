package me.alpha432.oyvey.features.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.alpha432.oyvey.Sped;
import me.alpha432.oyvey.features.command.Command;

public class HelpCommand
        extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(String[] commands) {
        HelpCommand.sendMessage("Commands: ");
        for (Command command : Sped.commandManager.getCommands()) {
            HelpCommand.sendMessage(ChatFormatting.GRAY + Sped.commandManager.getPrefix() + command.getName());
        }
    }
}

