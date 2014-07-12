package com.tenjava.entries.ase34.t3;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.util.concurrent.UncheckedExecutionException;
import com.tenjava.entries.ase34.t3.nms.BequeathingEntityPig;

public class TenJava extends JavaPlugin {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO Auto-generated method stub
        return super.onCommand(sender, command, label, args);
    }

    public void onDisable() {
        // TODO Auto-generated method stub
        super.onDisable();
    }

    public void onEnable() {
        try {
            EntityRegistrator.registerEntity("BequeathingPig", 90, BequeathingEntityPig.class);
        } catch (Exception e) {
            throw new UncheckedExecutionException(e);
        }
    }
}
