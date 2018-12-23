package ru.itis.kpfu.darZam.BattleRoyal.server;

import ru.itis.kpfu.darZam.BattleRoyal.gui.GameScreen;
import ru.itis.kpfu.darZam.BattleRoyal.player.MovingPlayer;
import ru.itis.kpfu.darZam.BattleRoyal.player.Player;

import java.io.*;
import java.net.Socket;


public class Clients implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private String addr;
    private int port;
    private GameScreen gameScreen;


    public Clients(GameScreen gameScreen, Socket socket) {
        this.gameScreen = gameScreen;
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            new ReadMsg().start();
        } catch (IOException e) {
            Clients.this.downService();
        }
    }




    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {}
    }

    @Override
    public void run() {

    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String str;
            try {
                while (true) {
                    str = in.readLine();
                    if(str.length()==1){

                    }
                    String[] position = str.split(":");
                    int positionX = Integer.parseInt(position[0]);
                    int positionY = Integer.parseInt(position[1]);
                    MovingPlayer player = new MovingPlayer(new Player());
                    player.setPositionX(positionX);
                    player.setPositionY(positionY);
                   // gameScreen.addPlayer(player);
                    System.out.println(str);
                }
            } catch (IOException e) {
                Clients.this.downService();
            }
        }
    }


}
