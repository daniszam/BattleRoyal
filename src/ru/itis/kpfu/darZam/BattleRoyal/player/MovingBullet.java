package ru.itis.kpfu.darZam.BattleRoyal.player;

import ru.itis.kpfu.darZam.BattleRoyal.weapon.Bullet;

public class MovingBullet {

    private Bullet bullet;
    private int positionX;
    private int positionY;
    private Direction direction;

    public Bullet getBullet() {
        return bullet;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public MovingBullet(Bullet bullet){
        this.bullet = bullet;
    }

    public void setDirection(Direction direction){
        switch (direction){
            case UP:
                bullet.setImage(Bullet.Icon.UP);
                break;
            case DOWN:
                bullet.setImage(Bullet.Icon.DOWN);
                break;
            case LEFT:
                bullet.setImage(Bullet.Icon.LEFT);
                break;
            case RIGHT:
                bullet.setImage(Bullet.Icon.RIGHT);
                break;
        }
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public enum Direction{
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
