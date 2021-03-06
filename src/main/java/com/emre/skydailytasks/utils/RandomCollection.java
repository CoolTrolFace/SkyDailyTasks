package com.emre.skydailytasks.utils;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomCollection<T extends Object> {

    private class Entry {
        double accumulatedWeight;
        T object;
    }

    private List<Entry> entries = new ArrayList<>();
    private double accumulatedWeight;
    private Random rand = new Random();

    public void add(T object, double weight) {
        accumulatedWeight += weight;
        Entry e = new Entry();
        e.object = object;
        e.accumulatedWeight = accumulatedWeight;
        entries.add(e);
    }
    public int size(){
        return entries.size();
    }
    public List<Entry> getEntries(){
        return this.entries;
    }

    public T getRandom() {
        double r = rand.nextDouble() * accumulatedWeight;

        for (Entry entry: entries) {
            if (entry.accumulatedWeight >= r) {
                return entry.object;
            }
        }
        return null; //should only happen when there are no entries
    }
}
