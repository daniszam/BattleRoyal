package ru.itis.kpfu.darZam.BattleRoyal.utils;

import ru.itis.kpfu.darZam.BattleRoyal.gui.GameScreen;

import java.util.TimerTask;

public class Task extends TimerTask {
    private GameScreen gameScreen;
    public Task(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    public void run() {
        gameScreen.drawBox();
    }
}