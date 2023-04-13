package fr.lavapower.eogendessentials;

import fr.lavapower.eogendessentials.command.*;
import fr.lavapower.eogendessentials.configuration.Configuration;
import fr.lavapower.eogendessentials.listener.BagListener;
import fr.lavapower.eogendessentials.listener.OtherListener;
import fr.lavapower.eogendessentials.listener.PlayerListener;
import fr.lavapower.eogendessentials.listener.ShulkerListener;
import fr.lavapower.eogendessentials.manager.VanishManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class EogendEssentials extends JavaPlugin {
    private Configuration configuration;
    private VanishManager vanishManager;

    @Override
    public void onEnable() {
        // Setup config
        saveDefaultConfig();
        configuration = Configuration.fromConfig(getConfig());

        // Register commands
        registerCommand("askroll", new AskRollCommand());
        registerCommand("dataitem", new DataItemCommand());
        registerCommand("lore", new LoreCommand());
        registerCommand("players", new PlayersCommand());
        registerCommand("renameitem", new RenameItemCommand());
        registerCommand("roll", new RollCommand(this));
        registerCommand("sendlink", new SendLinkCommand(this));
        registerCommand("tpworld", new TpWorldCommand());
        registerCommand("vanish", new VanishCommand(this));
        registerCommand("walk", new WalkCommand());
        registerCommand("reloadee", new ReloadEECommand(this));

        // Register Events
        getServer().getPluginManager().registerEvents(new BagListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new ShulkerListener(), this);
        getServer().getPluginManager().registerEvents(new OtherListener(), this);

        // Create Manager
        vanishManager = new VanishManager(this);
    }

    public void reload() {
        reloadConfig();
        configuration = Configuration.fromConfig(getConfig());
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public VanishManager getVanishManager()
    {
        return vanishManager;
    }

    private <T extends CommandExecutor & TabCompleter> void registerCommand(String name, T command) {
        PluginCommand pluginCommand = getCommand(name);
        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);
    }
}
