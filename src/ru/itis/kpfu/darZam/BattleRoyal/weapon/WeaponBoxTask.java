package ru.itis.kpfu.darZam.BattleRoyal.weapon;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WeaponBoxTask {

    private long time;
    private Timer timer;
    private Task task;

    public WeaponBoxTask(long time){
        this.time = time; // delay in milliseconds
        task = new Task();
        timer = new Timer("WeaponTask");
    }


    public void start() {
        timer.cancel();
        timer = new Timer("TaskName");
        Date executionDate = new Date(); // no params = now
        timer.scheduleAtFixedRate(task, executionDate, time);
    }

    private class Task extends TimerTask {
        public void run() {
            System.out.println("This message will print every 10 seconds.");
        }
    }

//    public static void main(String[] args) {
//        ClassExecutingTask executingTask = new ClassExecutingTask();
//        executingTask.start();
//    }

}
