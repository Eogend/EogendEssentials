package fr.lavapower.eogendessentials.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public record Configuration(List<String> fly) {
    public static Configuration fromConfig(FileConfiguration config) {
        return new Configuration(config.getStringList("fly"));
    }
}
