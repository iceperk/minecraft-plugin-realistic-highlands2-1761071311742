
package com.realistichighlands2;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class RealisticHighlands2 extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "RealisticHighlands2 plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "RealisticHighlands2 plugin disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("generatehighlands")) {
            if (args.length == 1) {
                String worldName = args[0];
                sender.sendMessage("Generating new world: " + worldName + " with RealisticHighlands2 generator...");
                WorldCreator creator = new WorldCreator(worldName);
                creator.generator(new HighlandsChunkGenerator());
                Bukkit.createWorld(creator);
                sender.sendMessage("World " + worldName + " generated successfully!");
                return true;
            } else {
                sender.sendMessage("Usage: /generatehighlands <worldname>");
                return false;
            }
        }
        return false;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new HighlandsChunkGenerator();
    }
}
