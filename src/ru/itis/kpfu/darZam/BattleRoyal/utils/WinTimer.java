package ru.itis.kpfu.darZam.BattleRoyal.utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinTimer  {

    private JLabel label;
    private Timer countdownTimer;
    int timeRemaining = 5 * 10 * 60;


    public WinTimer(JLabel passedLabel) {
        countdownTimer = new Timer(1000 * 5 * 60, new CountdownTimerListener());
        this.label = passedLabel;
        countdownTimer.start();
    }

    private class CountdownTimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (--timeRemaining > 0) {
                label.setText(String.valueOf(timeRemaining));
            } else {
                label.setText("Time's up!");
                countdownTimer.stop();
            }
        }
    }
}

