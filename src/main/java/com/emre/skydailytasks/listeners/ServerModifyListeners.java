package com.emre.skydailytasks.listeners;

import com.emre.skydailytasks.DailyTaskMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ServerModifyListeners implements Listener {


    @EventHandler
    public void JoinMessage(PlayerJoinEvent e){
        DailyTaskMain.playerManager.loadPlayerData(e.getPlayer().getName());
    }
    @EventHandler
    public void QuitMessage(PlayerQuitEvent e){
        DailyTaskMain.playerManager.unloadPlayerData(e.getPlayer().getName());
    }


}