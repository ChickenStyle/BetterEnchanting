package me.chickenstyle.enchanting.configs;

import me.chickenstyle.enchanting.BetterEnchanting;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LanguageConfig {

    private File file;
    private YamlConfiguration config;

    public LanguageConfig(BetterEnchanting main) {

        main.saveResource("lang.yml",false);
        file = new File(main.getDataFolder(), "lang.yml");
        config = YamlConfiguration.loadConfiguration(file);

    }

    public String getMessage(String path) {
        return config.getString("messages." + path);
    }

    public void reloadConfig() {
        try {
            config.save(file);
            config = YamlConfiguration.loadConfiguration(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
