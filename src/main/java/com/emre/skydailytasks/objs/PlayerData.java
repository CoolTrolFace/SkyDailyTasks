package com.emre.skydailytasks.objs;

public class PlayerData {

    String playername;
    String task1, task2, task3, task4, prize1, prize2, prize3, prize4;
    boolean stat1, stat2, stat3, stat4;

    public PlayerData(String playername, String task1Key, String task2Key, String task3Key, String task4Key, String prize1Key, String prize2Key, String prize3Key, String prize4Key, boolean stat1, boolean stat2, boolean stat3, boolean stat4 ){
        this.playername=playername;
        this.task1=task1Key;
        this.task2=task2Key;
        this.task3=task3Key;
        this.task4=task4Key;
        this.prize1=prize1Key;
        this.prize2=prize2Key;
        this.prize3=prize3Key;
        this.prize4=prize4Key;
        this.stat1=stat1;
        this.stat2=stat2;
        this.stat3=stat3;
        this.stat4=stat4;
    }

    public void completeTask1(){
        this.stat1=true;
    }
    public void completeTask2(){
        this.stat2=true;
    }
    public void completeTask3(){
        this.stat3=true;
    }
    public void completeTask4(){
        this.stat4=true;
    }

    public String getTask1() {
        return task1;
    }

    public String getTask2() {
        return task2;
    }

    public String getTask3() {
        return task3;
    }

    public String getTask4() {
        return task4;
    }

    public boolean isStat1() {
        return stat1;
    }

    public boolean isStat2() {
        return stat2;
    }

    public boolean isStat3() {
        return stat3;
    }

    public boolean isStat4() {
        return stat4;
    }

    public String getPrize1() {
        return prize1;
    }

    public String getPrize2() {
        return prize2;
    }

    public String getPrize3() {
        return prize3;
    }

    public String getPrize4() {
        return prize4;
    }
}
