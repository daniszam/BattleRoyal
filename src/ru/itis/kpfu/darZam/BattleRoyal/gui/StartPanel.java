package ru.itis.kpfu.darZam.BattleRoyal.gui;

import ru.itis.kpfu.darZam.BattleRoyal.player.MovingPlayer;
import ru.itis.kpfu.darZam.BattleRoyal.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class StartPanel {

    private JFrame mainFrame;
    private Container mainPanel;
    //
    private JLabel address;
    private JLabel nick;
    private JTextField ipAddress;
    private JTextField nickname;
    //
    private JButton start;

    public void run(){
        mainFrame = new JFrame("Start");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = mainFrame.getContentPane();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill =GridBagConstraints.HORIZONTAL;
        //

        ipAddress = new JTextField();
        nickname = new JTextField();
        address = new JLabel("ip address");
        address.setLabelFor(ipAddress);
        nick = new JLabel("nickname");
        nick.setLabelFor(nickname);
        start = new JButton("start");
        start.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Socket socket = new Socket(InetAddress.getByName(ipAddress.getText()), 8081);
                    mainFrame.dispose();
                    GameScreen gameScreen = new GameScreen(new MovingPlayer(new Player()), socket, nickname.getText());
                    gameScreen.run();
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(mainFrame, "Sorry cannot get this connection! \n" + "Please try again!");
                }
            }
        });
        //
        mainPanel.add(address,gridBagConstraints);
        mainPanel.add(ipAddress,gridBagConstraints);
        mainPanel.add(nick,gridBagConstraints);
        mainPanel.add(nickname,gridBagConstraints);
        mainPanel.add(start,gridBagConstraints);
        //
        mainFrame.setSize(500,500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        StartPanel startPanel = new StartPanel();
        startPanel.run();
    }
}
