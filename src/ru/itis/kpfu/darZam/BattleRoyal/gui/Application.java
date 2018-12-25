package ru.itis.kpfu.darZam.BattleRoyal.gui;

import ru.itis.kpfu.darZam.BattleRoyal.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {

    public static final String BATTLEROYAL = "Battle Royal";

    private JFrame mainFrame;
    private JButton start;
    private JButton exit;


    private void createFrame(){

        //mainFrame
        mainFrame = new JFrame(BATTLEROYAL);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 400);
        //mainFramePanel
        Container mainPanel = mainFrame.getContentPane();
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill =GridBagConstraints.HORIZONTAL;

        //buttons
        start = new JButton("start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                StartPanel startPanel = new StartPanel();
                startPanel.run();
            }
        });
        exit = new JButton("exit");


        //action
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //add components to main panel
        mainPanel.add(start, gridBagConstraints);
        mainPanel.add(exit, gridBagConstraints);

        //center positioning by window
        mainFrame.setLocationRelativeTo(null);
        //mainFrame.pack();
        mainFrame.setVisible(true);

    }

    public static void main(String[] args) {
        Application application = new Application();
        application.createFrame();
    }

}
