package the.david.restartqueue;

import org.bukkit.World;
import org.bukkit.block.Block;

public class Utils {
    public static void updateBlocks(Block block) {
        World world = block.getWorld();

        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();

        world.getBlockAt(x, y - 1, z).getState().update();
        world.getBlockAt(x, y + 1, z).getState().update();

        world.getBlockAt(x - 1, y, z).getState().update();
        world.getBlockAt(x + 1, y, z).getState().update();

        world.getBlockAt(x, y, z - 1).getState().update();
        world.getBlockAt(x, y, z + 1).getState().update();

        world.getBlockAt(x + 1, y + 1, z + 1).getState().update();
        world.getBlockAt(x + 1, y + 1, z - 1).getState().update();
        world.getBlockAt(x - 1, y + 1, z + 1).getState().update();
        world.getBlockAt(x - 1, y + 1, z - 1).getState().update();

        world.getBlockAt(x + 1, y - 1, z + 1).getState().update();
        world.getBlockAt(x + 1, y - 1, z - 1).getState().update();
        world.getBlockAt(x - 1, y - 1, z + 1).getState().update();
        world.getBlockAt(x - 1, y - 1, z - 1).getState().update();

        world.getBlockAt(x, y + 2, z).getState().update();
        world.getBlockAt(x, y - 2, z).getState().update();

        world.getBlockAt(x + 2, y, z).getState().update();
        world.getBlockAt(x - 2, y, z).getState().update();

        world.getBlockAt(x , y, z + 2).getState().update();
        world.getBlockAt(x, y, z - 2).getState().update();
    }
}
