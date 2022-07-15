package the.david.restartqueue.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import the.david.restartqueue.Restartqueue;

public class CommandRQOff implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //final
        Restartqueue.leversOff();
        return true;
    }
}
