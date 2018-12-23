package ru.itis.kpfu.darZam.BattleRoyal.player;

public class MovingPlayer {

    private Player player;
    private int positionX;
    private int positionY;


    public MovingPlayer(Player player) {
        this.player = player;


    }

    public void up() {
        positionY--;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void down() {
        positionY++;
    }

    public void left() {
        positionX--;
    }

    public void right() {
        positionX++;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

}
