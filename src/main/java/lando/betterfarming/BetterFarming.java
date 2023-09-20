package lando.betterfarming;

import lando.betterfarming.Listeners.onBlockBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterFarming extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getPluginManager().registerEvents(new onBlockBreakListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
