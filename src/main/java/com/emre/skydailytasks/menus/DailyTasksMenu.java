package com.emre.skydailytasks.menus;

import com.emre.skydailytasks.DailyTaskMain;
import com.emre.skydailytasks.objs.PlayerData;
import com.emre.skydailytasks.utils.TimeManager;
import com.emre.skydailytasks.utils.YamlItem;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenWindow;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.command.ServerCommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class DailyTasksMenu implements InventoryProvider {

    String playername;
    String task1Key,task2Key,task3Key,task4Key;
    String prize1Key, prize2Key,prize3Key,prize4Key;
    boolean task1Stat,task2Stat,task3Stat,task4Stat;


    public DailyTasksMenu(String playername){
        this.playername=playername;
        PlayerData playerData = DailyTaskMain.playerManager.getPlayerData(playername);
        this.task1Key=playerData.getTask1();
        this.task2Key=playerData.getTask2();
        this.task3Key=playerData.getTask3();
        this.task4Key=playerData.getTask4();
        this.prize1Key=playerData.getPrize1();
        this.prize2Key=playerData.getPrize2();
        this.prize3Key=playerData.getPrize3();
        this.prize4Key=playerData.getPrize4();
        this.task1Stat=playerData.isStat1();
        this.task2Stat=playerData.isStat2();
        this.task3Stat=playerData.isStat3();
        this.task4Stat=playerData.isStat4();
    }

    public void init(Player player, InventoryContents contents) {
        YamlItem task1Item = new YamlItem("tasks."+task1Key,DailyTaskMain.tasks);
        YamlItem task2Item = new YamlItem("tasks."+task2Key,DailyTaskMain.tasks);
        YamlItem task3Item = new YamlItem("tasks."+task3Key,DailyTaskMain.tasks);
        YamlItem task4Item = new YamlItem("tasks."+task4Key,DailyTaskMain.tasks);
        if(prize1Key!=null){
            task1Item.addLore("&7• "+DailyTaskMain.tasks.getString("prizes."+prize1Key+".name"));
        }
        if(prize2Key!=null){
            task2Item.addLore("&7• "+DailyTaskMain.tasks.getString("prizes."+prize2Key+".name"));
        }
        if(prize3Key!=null){
            task3Item.addLore("&7• "+DailyTaskMain.tasks.getString("prizes."+prize3Key+".name"));
        }
        if(prize4Key!=null){
            task4Item.addLore("&7• "+DailyTaskMain.tasks.getString("prizes."+prize4Key+".name"));
        }
        if(!task1Stat){
            task1Item.addLore("");
            task1Item.addLore("&7• Durum: &aAktif");
            contents.set(1,1, ClickableItem.of(task1Item.complete(),inventoryClickEvent -> {
                player.closeInventory();
                HashMap<ItemStack,Integer> reqItems = new HashMap<>();
                for(String reqItemStr:DailyTaskMain.tasks.getStringList("tasks."+task1Key+".req")){
                    reqItems.put(new ItemStack(Material.valueOf(reqItemStr.split(";")[0]),1),Integer.parseInt(reqItemStr.split(";")[1]));
                }
                for(ItemStack req: reqItems.keySet()){
                    int reqCount = reqItems.get(req);
                    if(itemCountInInventory(player.getInventory(),req)<reqCount){
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO,50,1);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&3SkyblockTC &8» &cYeterli eşyaya sahip değilsiniz."));
                        return;
                    }
                }
                for(ItemStack req: reqItems.keySet()){
                    int reqCount = reqItems.get(req);
                    removeItems(player.getInventory(),req,reqCount);
                    DailyTaskMain.playerManager.getPlayerData(playername).completeTask1();
                    givePrizesToPlayer(playername,task1Key,prize1Key);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,50,1);
                }
            }));
        }else{
            task1Item.addLore("");
            task1Item.addLore("&7• Durum: &cTamamlandı");
            task1Item.setGlow(true);
            contents.set(1,1, ClickableItem.empty(task1Item.complete()));
        }
        if(!task2Stat){
            task2Item.addLore("");
            task2Item.addLore("&7• Durum: &aAktif");
            contents.set(1,3, ClickableItem.of(task2Item.complete(),inventoryClickEvent -> {
                player.closeInventory();
                HashMap<ItemStack,Integer> reqItems = new HashMap<>();
                for(String reqItemStr:DailyTaskMain.tasks.getStringList("tasks."+task2Key+".req")){
                    reqItems.put(new ItemStack(Material.valueOf(reqItemStr.split(";")[0]),1),Integer.parseInt(reqItemStr.split(";")[1]));
                }
                for(ItemStack req: reqItems.keySet()){
                    int reqCount = reqItems.get(req);
                    if(itemCountInInventory(player.getInventory(),req)<reqCount){
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO,50,1);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&3SkyblockTC &8» &cYeterli eşyaya sahip değilsiniz."));
                        return;
                    }
                }
                for(ItemStack req: reqItems.keySet()){
                    int reqCount = reqItems.get(req);
                    removeItems(player.getInventory(),req,reqCount);
                    DailyTaskMain.playerManager.getPlayerData(playername).completeTask2();
                    givePrizesToPlayer(playername,task2Key,prize2Key);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,50,1);
                }
            }));
        }else{
            task2Item.addLore("");
            task2Item.addLore("&7• Durum: &cTamamlandı");
            task2Item.setGlow(true);
            contents.set(1,3, ClickableItem.empty(task2Item.complete()));
        }
        if(!task3Stat){
            task3Item.addLore("");
            task3Item.addLore("&7• Durum: &aAktif");
            contents.set(1,5, ClickableItem.of(task3Item.complete(),inventoryClickEvent -> {
                player.closeInventory();
                HashMap<ItemStack,Integer> reqItems = new HashMap<>();
                for(String reqItemStr:DailyTaskMain.tasks.getStringList("tasks."+task3Key+".req")){
                    reqItems.put(new ItemStack(Material.valueOf(reqItemStr.split(";")[0]),1),Integer.parseInt(reqItemStr.split(";")[1]));
                }
                for(ItemStack req: reqItems.keySet()){
                    int reqCount = reqItems.get(req);
                    if(itemCountInInventory(player.getInventory(),req)<reqCount){
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO,50,1);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&3SkyblockTC &8» &cYeterli eşyaya sahip değilsiniz."));
                        return;
                    }
                }
                for(ItemStack req: reqItems.keySet()){
                    int reqCount = reqItems.get(req);
                    removeItems(player.getInventory(),req,reqCount);
                    DailyTaskMain.playerManager.getPlayerData(playername).completeTask3();
                    givePrizesToPlayer(playername,task3Key,prize3Key);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,50,1);
                }
            }));
        }else{
            task3Item.addLore("");
            task3Item.addLore("&7• Durum: &cTamamlandı");
            task3Item.setGlow(true);
            contents.set(1,5, ClickableItem.empty(task3Item.complete()));
        }
        if(!task4Stat){
            task4Item.addLore("");
            task4Item.addLore("&7• Durum: &aAktif");
            contents.set(1,7, ClickableItem.of(task4Item.complete(),inventoryClickEvent -> {
                player.closeInventory();
                HashMap<ItemStack,Integer> reqItems = new HashMap<>();
                for(String reqItemStr:DailyTaskMain.tasks.getStringList("tasks."+task4Key+".req")){
                    reqItems.put(new ItemStack(Material.valueOf(reqItemStr.split(";")[0]),1),Integer.parseInt(reqItemStr.split(";")[1]));
                }
                for(ItemStack req: reqItems.keySet()){
                    int reqCount = reqItems.get(req);
                    if(itemCountInInventory(player.getInventory(),req)<reqCount){
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO,50,1);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&3SkyblockTC &8» &cYeterli eşyaya sahip değilsiniz."));
                        return;
                    }
                }
                for(ItemStack req: reqItems.keySet()){
                    int reqCount = reqItems.get(req);
                    removeItems(player.getInventory(),req,reqCount);
                    DailyTaskMain.playerManager.getPlayerData(playername).completeTask4();
                    givePrizesToPlayer(playername,task4Key,prize4Key);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,50,1);
                }
            }));
        }else{
            task4Item.addLore("");
            task4Item.addLore("&7• Durum: &cTamamlandı");
            task4Item.setGlow(true);
            contents.set(1,7, ClickableItem.empty(task4Item.complete()));
        }
    }

    public void update(Player player, InventoryContents contents) {

    }

    public void givePrizesToPlayer(String playername, String taskKey, String prizeKey){
        int skyCoinAmount = DailyTaskMain.tasks.getInt("tasks."+taskKey+".skycoin");
        String skycoinCmd = "skycoin give %player% "+skyCoinAmount;
        Bukkit.getPlayer(playername).sendMessage(ChatColor.translateAlternateColorCodes('&',"&3SkyblockTC &8» &7SkyCoin kazandınız: &b"+skyCoinAmount));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),skycoinCmd.replace("%player%",playername));
        if(prizeKey!=null){
            String cmd = DailyTaskMain.tasks.getString("prizes."+prizeKey+".command");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmd.replace("%player%",playername));
        }
    }

    public static int itemCountInInventory(PlayerInventory inventory, ItemStack item){
        int itemCountInPlayersInventory = 0;
        for (ItemStack stack : inventory.getContents()) {
            if (stack != null && stack.isSimilar(item)) {
                itemCountInPlayersInventory += stack.getAmount();
            }
        }
        return itemCountInPlayersInventory;
    }

    public static void removeItems(PlayerInventory inventory, ItemStack item, int amount) {
        if (amount <= 0) return;
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (is.isSimilar(item)) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    break;
                } else {
                    inventory.clear(slot);
                    amount = -newAmount;
                    if (amount == 0) break;
                }
            }
        }
    }
}
