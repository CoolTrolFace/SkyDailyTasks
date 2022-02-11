package com.emre.skydailytasks;

import com.emre.skydailytasks.listeners.ServerModifyListeners;
import com.emre.skydailytasks.utils.RandomCollection;
import com.emre.skydailytasks.utils.TimeManager;
import com.emre.skydailytasks.utils.Yaml;
import com.emre.skydailytasks.utils.YamlItem;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class DailyTaskMain extends JavaPlugin {

    public static Yaml config, tasks;
    public static DailyTaskMain instance;
    public static PlayerManager playerManager;
    public static List<String> randomTask = new ArrayList<>();
    public static RandomCollection<String> randomPrize = new RandomCollection<>();
    public static TimeManager timeManager;
    private static Economy econ;

    public void onEnable() {
        instance = this;
        config = new Yaml(getDataFolder() + "/config.yml", "config.yml");
        tasks = new Yaml(getDataFolder() + "/tasks.yml", "tasks.yml");
        timeManager = new TimeManager();
        randomTask.addAll(tasks.getConfigurationSection("tasks").getKeys(false));
        for(String prizeId : tasks.getConfigurationSection("prizes").getKeys(false)){
            randomPrize.add(prizeId,1);
        }
        randomPrize.add(null,randomPrize.size());
        this.getCommand("günlükgörev").setExecutor((CommandExecutor) new TaskCmds());
        playerManager = new PlayerManager(randomTask,randomPrize);
        Bukkit.getPluginManager().registerEvents((Listener) new ServerModifyListeners(),(Plugin) this);
    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }


    public void onDisable() {
        playerManager.unloadAllPlayers();
    }
}
