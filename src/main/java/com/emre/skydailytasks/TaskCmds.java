package com.emre.skydailytasks;

import com.emre.skydailytasks.menus.DailyTasksMenu;
import com.emre.skydailytasks.utils.TimeManager;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TaskCmds implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = null;
        if(sender instanceof Player){
            player = ((Player) sender).getPlayer();
        }
        if(args.length==0){
            SmartInventory INVENTORY = SmartInventory.builder() //  Builds the menu
                    .id("günlükgörev")
                    .provider(new DailyTasksMenu(player.getName()))
                    .size(3, 9)
                    .title(ChatColor.BLACK + TimeManager.getTimeLefttoTomorrowStr())
                    .build();
            INVENTORY.open(player); //    Opens the menu
        }else if(args.length==1){
            if(args[0].equalsIgnoreCase("reset")){
                if(player.hasPermission("skyblocktc.admin")||sender instanceof ConsoleCommandSender){
                    DailyTaskMain.playerManager.renewAllPlayerData();
                }
            }
        }


        return true;
    }
}
