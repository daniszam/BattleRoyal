package ru.itis.kpfu.darZam.BattleRoyal.gui;

import ru.itis.kpfu.darZam.BattleRoyal.player.MovingPlayer;
import ru.itis.kpfu.darZam.BattleRoyal.player.Player;
import ru.itis.kpfu.darZam.BattleRoyal.server.Clients;
import ru.itis.kpfu.darZam.BattleRoyal.weapon.Pistol;
import ru.itis.kpfu.darZam.BattleRoyal.weapon.WeaponBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class GameScreen extends JComponent implements ActionListener {

    private Timer timer = new Timer(15, this);
    private MovingPlayer player;
    private String nickname;
    private int id;
    //position
    private boolean isLeft;
    private boolean isRight;
    private boolean isUp;
    private boolean isDown;
    //task
    private long time;
    private java.util.Timer taskTimer;
    private Task task;
    private WeaponBox weaponBox;
    private boolean isPaintedBox;
    private int weaponBoxX;
    private int weaponBoxY;
    private HashMap<String, MovingPlayer> players;
    //size
    private final int PLAYER_WIDTH = 60;
    private final int PLAYER_HEIGHT = 60;
    private final int WEAPONBOX_HEIGHT = 40;
    private final int WEAPONBOX_WIDTH = 40;
    //socket
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private BufferedReader bf;


    public GameScreen(MovingPlayer player, Socket socket, String nickname) throws IOException {
        this.player = player;
        this.socket = socket;
        this.nickname = nickname;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        players = new HashMap<>();
        new ReadMsg().start();
        ///  Clients clients = new Clients(this, socket);

        //position
        this.isDown = false;
        this.isLeft = false;
        this.isRight = false;
        this.isUp = false;
        setTask();
        bindKeys();

    }

    private void bindKeys() {
        InputMap inputMap = this.getInputMap();
        ActionMap actionMap = this.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "up release");

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "down release");

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "left release");

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "right release");

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "shot");
        actionMap.put("shot", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.getPlayer().attack();
            }
        });

        actionMap.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isUp = true;
                start();
            }
        });

        actionMap.put("up release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isUp = false;
                stop();
            }
        });

        actionMap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("aaa");
                isDown = true;
                start();
            }
        });

        actionMap.put("down release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("sss");
                isDown = false;
                stop();
            }
        });

        actionMap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isLeft = true;
                start();
            }
        });

        actionMap.put("left release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isLeft = false;
                stop();
            }
        });

        actionMap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRight = true;
                start();
            }
        });

        actionMap.put("right release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRight = false;
                stop();
            }
        });
    }


    private void setTask() {
        this.time = 10000; // delay in milliseconds
        task = new Task(this);
        taskTimer = new java.util.Timer("WeaponTask");
        this.weaponBox = new WeaponBox();
        weaponBox.addWeapon(new Pistol());

        taskTimer.cancel();
        taskTimer = new java.util.Timer("TaskName");
        Date executionDate = new Date(); // no params = now
        taskTimer.scheduleAtFixedRate(task, executionDate, time);
    }


    private class Task extends TimerTask {
        private GameScreen gameScreen;

        public Task(GameScreen gameScreen) {
            this.gameScreen = gameScreen;
        }

        public void run() {
            gameScreen.drawBox();
        }
    }

    private void drawBox() {
        isPaintedBox = false;
        repaint();
    }

    public void addPlayer(MovingPlayer player, String nickname) {
        players.put(nickname, player);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isUp) {
            if (player.getPositionY() <= 0) {
                player.setPositionY(0);
            } else {
                player.up();
            }
        } else if (isDown) {
            if (player.getPositionY() > getHeight()) {
                player.setPositionY(getHeight());
            } else {
                player.down();
            }
        }
        if (isRight) {
            if (player.getPositionX() > getWidth()) {
                player.setPositionX(getWidth());
            } else {
                player.right();
            }
        } else if (isLeft) {
            if (player.getPositionX() < 0) {
                player.setPositionX(0);
            } else {
                player.left();
            }
        }
        try {
            dataOutputStream.writeUTF(player.getPositionX() + ":" + player.getPositionY() + ":" + id);
            dataOutputStream.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        repaint();
        checkWeaponBox();
    }

    private void checkWeaponBox() {
        if (weaponBox != null) {
            int playerStartX = player.getPositionX();
            int playerEndX = playerStartX + PLAYER_WIDTH;
            int playerStartY = player.getPositionY();
            int playerEndY = playerStartY + PLAYER_HEIGHT;
            int weaponBoxStartX = weaponBoxX;
            int weaponBoxEndX = weaponBoxX + WEAPONBOX_WIDTH;
            int weaponBoxStartY = weaponBoxY;
            int weaponBoxEndY = weaponBoxY + WEAPONBOX_HEIGHT;
            if (((weaponBoxStartX > playerStartX && weaponBoxStartX < playerEndX) ||
                    (weaponBoxEndX > playerStartX && weaponBoxEndX < playerEndX)) &&
                    ((weaponBoxStartY > playerStartY && weaponBoxStartY < playerEndY) ||
                            (weaponBoxEndY > playerStartY && weaponBoxEndY < playerEndY))) {
                player.getPlayer().setWeapon(weaponBox.getRandom());
                weaponBox = null;
                System.out.println(player.getPlayer().getWeapon());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        System.out.println(timer.isRunning());
        System.out.println(isDown + " " + isLeft + " " + isUp + " " + isRight);
        graphics2D.drawImage(player.getPlayer().getIcon(), player.getPositionX(), player.getPositionY(),
                PLAYER_WIDTH, PLAYER_HEIGHT, null);
        if (player.getPlayer().getWeapon() != null) {
            graphics2D.drawImage(player.getPlayer().getWeapon().getWeaponIcon(), player.getPositionX() + PLAYER_WIDTH / 2,
                    player.getPositionY() + PLAYER_HEIGHT / 2, 20, 20, null);
        }

        if (!isPaintedBox) {
            weaponBoxX = (int) (Math.random() * this.getHeight());
            weaponBoxY = (int) (Math.random() * this.getWidth());
            isPaintedBox = true;
        }
        if (weaponBox != null) {
            graphics2D.drawImage(weaponBox.getIcon(), weaponBoxX, weaponBoxY, WEAPONBOX_WIDTH, WEAPONBOX_HEIGHT, null);
        }
        if (players.size() > 0) {
            for (Map.Entry item : players.entrySet()) {
                MovingPlayer player = (MovingPlayer) item.getValue();
                graphics2D.drawImage(player.getPlayer().getIcon(), player.getPositionX(),
                        player.getPositionY(), PLAYER_WIDTH, PLAYER_HEIGHT, null);
            }
        }
    }

    private void start() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }


    private void stop() {
        if (!isUp && !isRight &&
                !isDown && !isLeft) {
            timer.stop();
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("JustGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        GameScreen movingPlayer = null;
        try {
            Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 8081);
            movingPlayer = new GameScreen(new MovingPlayer(new Player()), socket, "vasya");
            // Clients clients = new Clients(movingPlayer, socket);
            //   new Thread(clients).start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        movingPlayer.setBackground(Color.BLACK);
        movingPlayer.setSize(20, 20);

        frame.getContentPane().add(movingPlayer, BorderLayout.CENTER);
        movingPlayer.setFocusable(true);
        frame.setVisible(true);
    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String str;
            try {
                while (true) {
                    System.out.println("1");
                    str = bf.readLine();
                    if(str.length()==1){
                        id = Integer.parseInt(str);
                        continue;
                    }
                    System.out.println(str);
                    String[] position = str.split(":");
                    if (!position[2].equals(id)) {
                        int positionX = Integer.parseInt(position[0]);
                        int positionY = Integer.parseInt(position[1]);
                        MovingPlayer player = new MovingPlayer(new Player());
                        player.setPositionX(positionX);
                        player.setPositionY(positionY);
                        addPlayer(player, position[2]);
                        repaint();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

