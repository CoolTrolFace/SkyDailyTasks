package com.emre.skydailytasks.utils;

import com.emre.skydailytasks.DailyTaskMain;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeManager {

    public static int currentDay;
    private final Calendar calendar = Calendar.getInstance();

    public TimeManager(){
        SimpleDateFormat dayFormat =new SimpleDateFormat("D", new Locale("tr", "TR"));
        currentDay = Integer.parseInt(dayFormat.format(new Date()));

        new BukkitRunnable(){
            @Override
            public void run() {
                int newDay = Integer.parseInt(dayFormat.format(new Date()));
                if(newDay!=currentDay){
                    currentDay=newDay;
                    DailyTaskMain.playerManager.renewAllPlayerData();
                }

            }
        }.runTaskTimerAsynchronously(DailyTaskMain.instance,0,150L);
    }

    public static String getTimeLefttoTomorrowStr(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long howMany = (c.getTimeInMillis()-System.currentTimeMillis());
        int seconds = (int) (howMany / 1000) % 60 ;
        int minutes = (int) ((howMany / (1000*60)) % 60);
        int hours   = (int) ((howMany / (1000*60*60)) % 24);
        return "Yenilenmesine "+hours+" saat, "+minutes+" dakika";
    }

    public static int getCurrentDay() {
        return currentDay;
    }
}