package ru.itis.kpfu.darZam.BattleRoyal.messages;

import ru.itis.kpfu.darZam.BattleRoyal.player.MovingBullet;
import ru.itis.kpfu.darZam.BattleRoyal.weapon.Bullet;

public class BulletMessage {

    public static MovingBullet getBullet(String[] response) {
        MovingBullet movingBullet = new MovingBullet(new Bullet(Integer.parseInt(response[4])));
        movingBullet.setPositionX(Integer.parseInt(response[1]));
        movingBullet.setPositionY(Integer.parseInt(response[2]));
        switch (response[6]) {
            case ("RIGHT"):
                movingBullet.setDirection(MovingBullet.Direction.RIGHT);
                break;
            case ("LEFT"):
                movingBullet.setDirection(MovingBullet.Direction.LEFT);
                break;
            case ("DOWN"):
                movingBullet.setDirection(MovingBullet.Direction.DOWN);
                break;
            case ("UP"):
                movingBullet.setDirection(MovingBullet.Direction.UP);
                break;
            default:
                movingBullet.setDirection(MovingBullet.Direction.RIGHT);
                break;
        }
        return movingBullet;
    }
}
