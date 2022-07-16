package the.david.restartqueue.db;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.block.Block;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LeverStoradge {
    private File file;

    private JSONArray json;

    private JSONParser parser = new JSONParser();

    public LeverStoradge() {
        if (!Files.exists(Paths.get("plugins/restartqueue", new String[0]), new java.nio.file.LinkOption[0]))
            (new File("plugins/restartqueue")).mkdirs();
        this.file = new File("plugins/restartqueue/levers.json");
        if (!Files.exists(Paths.get("plugins/restartqueue/levers.json", new String[0]), new java.nio.file.LinkOption[0]))
            try {
                this.file.createNewFile();
                FileWriter fw = new FileWriter("plugins/restartqueue/levers.json");
                fw.write("[]");
                fw.close();
            } catch (IOException e) {
                System.out.println("Critical error occurred when trying to create a file to store lever data in.");
                e.printStackTrace();
            }
        try {
            this.json = (JSONArray)this.parser.parse(new FileReader("plugins/restartqueue/levers.json"));
        } catch (IOException|org.json.simple.parser.ParseException e) {
            System.out.println("Critical errors occured when trying to read levers.json");
            e.printStackTrace();
        }
    }

    public void addLever(Block lever, String name, String uuid) {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("todo", Integer.valueOf(0));
        obj.put("creator", uuid);
        obj.put("world_name", lever.getWorld().getName());
        List<String> location = new ArrayList<>();
        location.add(String.valueOf(lever.getX()));
        location.add(String.valueOf(lever.getY()));
        location.add(String.valueOf(lever.getZ()));
        obj.put("location", location);
        try {
            this.json = (JSONArray)this.parser.parse(new FileReader("plugins/restartqueue/levers.json"));
        } catch (IOException|org.json.simple.parser.ParseException e) {
            System.out.println("Critical errors occured when trying to read levers.json");
            e.printStackTrace();
        }
        this.json.add(obj);
        try {
            FileWriter fw = new FileWriter(this.file);
            fw.write(this.json.toJSONString());
            fw.close();
        } catch (IOException e) {
            System.out.println("Critical errors occured when trying to write to levers.json");
            e.printStackTrace();
        }
    }

    public JSONArray getArrayFromFile() {
        try {
            this.json = (JSONArray)this.parser.parse(new FileReader("plugins/restartqueue/levers.json"));
        } catch (IOException|org.json.simple.parser.ParseException e) {
            System.out.println("Critical errors occured when trying to read levers.json");
            e.printStackTrace();
        }
        return this.json;
    }

    public void writeFile(JSONArray json) {
        try {
            FileWriter fw = new FileWriter(this.file);
            fw.write(json.toJSONString());
            fw.close();
        } catch (IOException e) {
            System.out.println("Critical errors occured when trying to write to levers.json");
            e.printStackTrace();
        }
    }
}
