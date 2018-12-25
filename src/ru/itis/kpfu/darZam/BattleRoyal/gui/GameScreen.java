package ru.itis.kpfu.darZam.BattleRoyal.gui;

import ru.itis.kpfu.darZam.BattleRoyal.messages.BulletMessage;
import ru.itis.kpfu.darZam.BattleRoyal.messages.PlayerMessage;
import ru.itis.kpfu.darZam.BattleRoyal.player.MovingBullet;
import ru.itis.kpfu.darZam.BattleRoyal.player.MovingPlayer;
import ru.itis.kpfu.darZam.BattleRoyal.player.Player;
import ru.itis.kpfu.darZam.BattleRoyal.utils.Task;
import ru.itis.kpfu.darZam.BattleRoyal.utils.WinTimer;
import ru.itis.kpfu.darZam.BattleRoyal.weapon.Bullet;
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


public class GameScreen extends JPanel implements ActionListener {

    private Timer timer = new Timer(15, this);
    private final Image  board= new ImageIcon("C:\\Users\\danis\\Desktop\\BattleRoyal\\BattleRoyal\\" +
            "src\\ru\\itis\\kpfu\\darZam\\BattleRoyal\\resources\\image\\board.jpg").getImage();
    private MovingPlayer player;
    private String nickname;
    private int id;
    private WinTimer timerToWin;
    private JLabel timerToWinLabel;
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
    private final int BULLET_WIDTH = 30;
    private final int BULLET_HEIGHT=30;
    //socket
    private DataOutputStream dataOutputStream;
    private BufferedReader bf;
    //bullet
    private HashMap<String, MovingBullet> bullets;
    private int bulletId;


    public GameScreen(MovingPlayer player, Socket socket, String nickname) throws IOException {
        this.player = player;
        this.nickname = nickname;
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        players = new HashMap<>();
        bullets = new HashMap<>();
        bulletId = 0;
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
                int damage = player.getPlayer().attack();
                if(damage>0){
                    MovingBullet movingBullet = new MovingBullet(new Bullet(damage));
                    movingBullet.setDirection(MovingBullet.Direction.RIGHT);
                    movingBullet.setPositionX(player.getPositionX()+PLAYER_WIDTH/2);
                    movingBullet.setPositionY(player.getPositionY()+PLAYER_HEIGHT/2);
                    if(isLeft){
                        movingBullet.setDirection(MovingBullet.Direction.LEFT);
                    }else if (isRight){
                        movingBullet.setDirection(MovingBullet.Direction.RIGHT);
                    }
                    if(isUp){
                        movingBullet.setDirection(MovingBullet.Direction.UP);
                    }else if (isDown){
                        movingBullet.setDirection(MovingBullet.Direction.DOWN);
                    }
                    bullets.put(String.valueOf(id) +
                           ":" +String.valueOf(bulletId), movingBullet);
                    bulletId++;


                    try {
                        dataOutputStream.writeUTF(
                                "bullet:"+movingBullet.getPositionX() + ":" +
                                        movingBullet.getPositionY() + ":" +
                                        id +":"+
                                        movingBullet.getBullet().getDamage() + ":" +
                                        bulletId + ":" + movingBullet.getDirection());
                        dataOutputStream.flush();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }



                    start();
                }
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

    public void drawBox() {
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
            }
        }
    }

    private void checkHit(){

            int playerStartX = player.getPositionX();
            int playerEndX = playerStartX + PLAYER_WIDTH;
            int playerStartY = player.getPositionY();
            int playerEndY = playerStartY + PLAYER_HEIGHT;
            for (Map.Entry bullet: bullets.entrySet()) {
                MovingBullet mb = (MovingBullet) bullet.getValue();
                System.out.println(bullet.getKey());
                if (Integer.parseInt(bullet.getKey().toString().split(":")[0]) != id) {
                    int bulletStartX = mb.getPositionX();
                    int bulletEndX = mb.getPositionX() + BULLET_WIDTH;
                    int bulletStartY = mb.getPositionY();
                    int bulletEndY = mb.getPositionY() + BULLET_HEIGHT;
                    if (((bulletStartX > playerStartX && bulletStartX < playerEndX) ||
                            (bulletEndX > playerStartX && bulletEndX < playerEndX)) &&
                            ((bulletStartY > playerStartY && bulletStartY < playerEndY) ||
                                    (bulletEndY > playerStartY && bulletEndY < playerEndY))) {

                        if (mb.getBullet().hitPlayer(player.getPlayer()) <= 0) {
                            JOptionPane.showConfirmDialog(this, "You Died");
                        }
                        bullets.remove(bullet.getKey());
                    }
                }
            }

    }

    private void moveBullets(){
        for (Map.Entry item: bullets.entrySet()){
            MovingBullet mb =(MovingBullet) item.getValue();
            switch (mb.getDirection()) {
                case UP:
                    mb.setPositionY(mb.getPositionY() - 5);
                    break;
                case DOWN:
                    mb.setPositionY(mb.getPositionY() + 5);
                    break;
                case LEFT:
                    mb.setPositionX(mb.getPositionX() - 5);
                    break;
                case RIGHT:
                    mb.setPositionX(mb.getPositionX() + 5);
                    break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        moveBullets();
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(board, 0,0, null);
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
            graphics2D.drawImage(weaponBox.getIcon(), weaponBoxX, weaponBoxY,
                    WEAPONBOX_WIDTH, WEAPONBOX_HEIGHT, null);
        }
        for (Map.Entry item : players.entrySet()) {
            MovingPlayer player = (MovingPlayer) item.getValue();
            graphics2D.drawImage(player.getPlayer().getIcon(), player.getPositionX(),
                    player.getPositionY(), PLAYER_WIDTH, PLAYER_HEIGHT, null);
        }
        for (int i =0; i<bullets.size() ; i++){
            String key =(String) bullets.keySet().toArray()[i];
            MovingBullet mb = bullets.get(key);
            if (mb.getPositionX()>=getWidth() || mb.getPositionX()<=0
                    || mb.getPositionY()>=getHeight() || mb.getPositionY()<=0){
                bullets.remove(key);
            }else {
                graphics2D.drawImage(mb.getBullet().getIcon(), mb.getPositionX(),
                        mb.getPositionY(), BULLET_WIDTH, BULLET_HEIGHT, null);
            }
        }

        checkWeaponBox();
        checkHit();

    }

    private void start() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }


    private void stop() {
        if (!isUp && !isRight &&
                !isDown && !isLeft && bullets.size()==0) {
            timer.stop();
        }
    }


    public void run(){
        JFrame frame = new JFrame("JustGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 900);
        frame.setResizable(false);
        frame.getContentPane().add(this, BorderLayout.CENTER);
        this.setFocusable(true);
        frame.setVisible(true);
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("JustGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 900);
        frame.setResizable(false);
        GameScreen movingPlayer = null;
        try {
            Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 8081);
            movingPlayer = new GameScreen(new MovingPlayer(new Player()), socket, "vasya");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel labelToWinTimer = new JLabel();
        WinTimer timer = new WinTimer(labelToWinTimer);
        frame.getContentPane().add(labelToWinTimer, BorderLayout.WEST);
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
                    str = bf.readLine();
                    if(str.length()==1){
                        id = Integer.parseInt(str);
                        continue;
                    }
                    System.out.println(str);
                    String[] response = str.split(":");
                    if(response[0].equals("bullet") ){
                        if (Integer.parseInt(response[3])!=id) {
                            MovingBullet mb = BulletMessage.getBullet(response);
                            bullets.put(response[3] + ":" +
                                    response[5], mb);//
                        }
                        continue;
                    }
                    if (!response[2].equals(id)) {
                        addPlayer(PlayerMessage.getPlayer(response), response[2]);
                        repaint();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

