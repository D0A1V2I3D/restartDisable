package the.david.restartqueue.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import the.david.restartqueue.Restartqueue;

public class CommandRQOff implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Restartqueue.leversOff();
        return true;
    }
}
