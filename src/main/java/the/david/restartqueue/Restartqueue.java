package the.david.restartqueue;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import the.david.restartqueue.command.CommandHelp;
import the.david.restartqueue.command.CommandRQ;
import the.david.restartqueue.command.CommandRQA;
import the.david.restartqueue.command.CommandRQOff;
import the.david.restartqueue.command.CommandRQOn;
import the.david.restartqueue.command.ConstructTabCompleter;
import the.david.restartqueue.command.ConstructTabCompleterRQA;
import the.david.restartqueue.db.LeverStoradge;

public final class Restartqueue extends JavaPlugin {
    public static LeverStoradge storadge = new LeverStoradge();

    public void onEnable() {
        getCommand("rq").setExecutor((CommandExecutor)new CommandRQ());
        getCommand("rq").setTabCompleter((TabCompleter)new ConstructTabCompleter());
        getCommand("rqoff").setExecutor((CommandExecutor)new CommandRQOff());
        getCommand("rqon").setExecutor((CommandExecutor)new CommandRQOn());
        getCommand("rqhelp").setExecutor((CommandExecutor)new CommandHelp());
        getCommand("rqa").setExecutor((CommandExecutor)new CommandRQA());
        getCommand("rqa").setTabCompleter((TabCompleter)new ConstructTabCompleterRQA());
    }

    public void onDisable() {}

    public static void leversOff() {
        JSONArray json = storadge.getArrayFromFile();
        List<Object> toRemove = new ArrayList();
        List<JSONObject> toAdd = new ArrayList<>();
        for (Object o : json) {
            JSONObject obj = (JSONObject)o;
            World world = Bukkit.getWorld(obj.get("world_name").toString());
            JSONArray loc = (JSONArray)obj.get("location");
            List<String> location = new ArrayList<>();
            for (int i = 0; i < 3; i++)
                location.add(loc.get(i).toString());
            Block block = world.getBlockAt(Integer.parseInt(location.get(0)), Integer.parseInt(location.get(1)), Integer.parseInt(location.get(2)));
            if (block.getType() != Material.LEVER) {
                toRemove.add(o);
                continue;
            }
            BlockData data = block.getBlockData();
            if (data instanceof Powerable) {
                Powerable powerable = (Powerable)data;
                if (powerable.isPowered()) {
                    ((Powerable)data).setPowered(false);
                    block.setBlockData(data);
                    obj.remove("todo");
                    obj.put("todo", Integer.valueOf(1));
                    toRemove.add(o);
                    toAdd.add(obj);
                }
            }
        }
        json.removeAll(toRemove);
        json.addAll(toAdd);
        storadge.writeFile(json);
    }

    public static void leversOn() {
        JSONArray json = storadge.getArrayFromFile();
        List<Object> toRemove = new ArrayList();
        List<JSONObject> toAdd = new ArrayList<>();
        for (Object o : json) {
            JSONObject obj = (JSONObject)o;
            World world = Bukkit.getWorld(obj.get("world_name").toString());
            JSONArray loc = (JSONArray)obj.get("location");
            List<String> location = new ArrayList<>();
            for (int i = 0; i < 3; i++)
                location.add(loc.get(i).toString());
            Block block = world.getBlockAt(Integer.parseInt(location.get(0)), Integer.parseInt(location.get(1)), Integer.parseInt(location.get(2)));
            if (block.getType() != Material.LEVER)
                toRemove.add(o);
            BlockData data = block.getBlockData();
            if (data instanceof Powerable) {
                Powerable powerable = (Powerable)data;
                if (!powerable.isPowered() &&
                        obj.get("todo").toString().equalsIgnoreCase("1")) {
                    ((Powerable)data).setPowered(true);
                    block.setBlockData(data);
                    obj.remove("todo");
                    obj.put("todo", Integer.valueOf(0));
                    toRemove.add(o);
                    toAdd.add(obj);
                }
            }
        }
        json.removeAll(toRemove);
        json.addAll(toAdd);
        storadge.writeFile(json);
    }
}
