package com.emre.skydailytasks;

import com.emre.skydailytasks.objs.PlayerData;
import com.emre.skydailytasks.utils.RandomCollection;
import com.emre.skydailytasks.utils.TimeManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PlayerManager {

    private static HashMap<String, PlayerData> playerDataHashMap = new HashMap<>();
    final List<String> randomTask;
    RandomCollection<String> randomPrize;

    PlayerManager(List<String> randomTask,RandomCollection<String> randomPrize){
        this.randomPrize=randomPrize;
        this.randomTask=randomTask;
        for(Player player : Bukkit.getOnlinePlayers()){
            loadPlayerData(player.getName());
        }
    }

    public void loadPlayerData(String playername){
        if (isExists(playername)) return;
        File dataFile = new File(DailyTaskMain.instance.getDataFolder(), "/data/" + playername + ".yml");
        if (!dataFile.exists()){
            createPlayerData(playername);
            return;
        }
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(dataFile);
        playerDataHashMap.put(playername,new PlayerData(playername,playerConfig.getString("task-1"),playerConfig.getString("task-2"),playerConfig.getString("task-3"),playerConfig.getString("task-4"),playerConfig.getString("prize-1"),playerConfig.getString("prize-2"),playerConfig.getString("prize-3"),playerConfig.getString("prize-4"),playerConfig.getBoolean("stat-1"),playerConfig.getBoolean("stat-2"),playerConfig.getBoolean("stat-3"),playerConfig.getBoolean("stat-4")));
        if(playerConfig.getInt("last-join") != TimeManager.getCurrentDay()){
            renewPlayerData(playername);
        }
    }
    public boolean isExists(String playername){
        return playerDataHashMap.containsKey(playername);
    }
    public void createPlayerData(String playername){
        if(isExists(playername)) return;
        File dataFile = new File(DailyTaskMain.instance.getDataFolder(), "/data/" + playername + ".yml");
        if (dataFile.exists()) return;
        PlayerData playerData = createRandomPlayerData(playername);
        playerDataHashMap.put(playername,playerData);
        FileConfiguration playerConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(dataFile);
        playerConfig.set("task-1",playerData.getTask1());
        playerConfig.set("prize-1",playerData.getPrize1());
        playerConfig.set("stat-1",playerData.isStat1());
        playerConfig.set("task-2",playerData.getTask2());
        playerConfig.set("prize-2",playerData.getPrize2());
        playerConfig.set("stat-2",playerData.isStat1());
        playerConfig.set("task-3",playerData.getTask3());
        playerConfig.set("prize-3",playerData.getPrize3());
        playerConfig.set("stat-3",playerData.isStat1());
        playerConfig.set("task-4",playerData.getTask4());
        playerConfig.set("prize-4",playerData.getPrize4());
        playerConfig.set("stat-4",playerData.isStat1());
        playerConfig.set("last-join", TimeManager.getCurrentDay());
        try {
            playerConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renewPlayerData(String playername){
        playerDataHashMap.replace(playername,createRandomPlayerData(playername));
    }
    public void renewAllPlayerData(){
        getPlayerDataHashMap().forEach((s, playerData) -> {
            playerDataHashMap.replace(s,createRandomPlayerData(s));
        });
    }

    public PlayerData getPlayerData(String playername){
        if(!isExists(playername)) return null;
        return playerDataHashMap.get(playername);
    }

    public PlayerData createRandomPlayerData(String playername){
        List<String> tempList = new ArrayList<>();
        tempList.addAll(randomTask);
        int chosen;
        Random rand = new Random();
        chosen = rand.nextInt(tempList.size());
        String task1 = tempList.get(chosen);
        tempList.remove(chosen);
        chosen = rand.nextInt(tempList.size());
        String task2 = tempList.get(chosen);
        tempList.remove(chosen);
        chosen = rand.nextInt(tempList.size());
        String task3 = tempList.get(chosen);
        tempList.remove(chosen);
        chosen = rand.nextInt(tempList.size());
        String task4 = tempList.get(chosen);
        tempList.remove(chosen);

        return new PlayerData(playername,task1,task2,task3,task4,DailyTaskMain.randomPrize.getRandom(),DailyTaskMain.randomPrize.getRandom(),DailyTaskMain.randomPrize.getRandom(),DailyTaskMain.randomPrize.getRandom(),false,false,false,false);
    }

    public void savePlayerData(String playername) {
        if (!isExists(playername)) return;
        File dataFile = new File(DailyTaskMain.instance.getDataFolder(), "/data/" + playername + ".yml");
        if (!dataFile.exists()) return;
        FileConfiguration playerConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(dataFile);
        PlayerData playerData = getPlayerData(playername);
        playerConfig.set("task-1",playerData.getTask1());
        playerConfig.set("prize-1",playerData.getPrize1());
        playerConfig.set("stat-1",playerData.isStat1());
        playerConfig.set("task-2",playerData.getTask2());
        playerConfig.set("prize-2",playerData.getPrize2());
        playerConfig.set("stat-2",playerData.isStat2());
        playerConfig.set("task-3",playerData.getTask3());
        playerConfig.set("prize-3",playerData.getPrize3());
        playerConfig.set("stat-3",playerData.isStat3());
        playerConfig.set("task-4",playerData.getTask4());
        playerConfig.set("prize-4",playerData.getPrize4());
        playerConfig.set("stat-4",playerData.isStat4());
        playerConfig.set("last-join",DailyTaskMain.timeManager.getCurrentDay());
        try {
            playerConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void unloadPlayerData(String playername) {
        if (!isExists(playername)) return;
        savePlayerData(playername);
        playerDataHashMap.remove(playername);
    }

    public void unloadAllPlayers(){
        getPlayerDataHashMap().forEach((s, playerData) -> {
            savePlayerData(s);
        });
    }

    public HashMap<String, PlayerData> getPlayerDataHashMap() {
        return playerDataHashMap;
    }
}
