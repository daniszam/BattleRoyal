package ru.itis.kpfu.darZam.BattleRoyal.player;


import ru.itis.kpfu.darZam.BattleRoyal.health.HealthPropertie;
import ru.itis.kpfu.darZam.BattleRoyal.weapon.WeaponItem;

import javax.swing.*;
import java.awt.*;

public class Player {

    private int healthPoint;
    private int damage;
    private WeaponItem weapon;
    private Image icon;

    public Player() {
        healthPoint = 100;
        damage = 5;
        icon = new ImageIcon("C:\\Users\\danis\\Desktop\\BattleRoyal\\BattleRoyal\\src\\ru\\itis\\kpfu\\darZam\\BattleRoyal\\resources\\image\\dino.png").getImage();

    }

    public Image getIcon() {
        return icon;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public int getDamage() {
        return damage;
    }

    public void setHealthPoint(int healthPoint) {
        if (healthPoint > 100) {
            this.healthPoint = 100;
        } else {
            this.healthPoint = healthPoint;
        }
    }

    public void setDamage(byte damage) {
        this.damage = damage;
    }

    public int attack() {
        if (weapon != null) {
            if (weapon.getMagazine() < 0) {
                weapon = null;
                return 0;
            }
            return weapon.shot();
        }
        return 0;
    }

    public WeaponItem getWeapon() {
        return weapon;
    }

    public void setWeapon(WeaponItem weapon) {
        this.weapon = weapon;
    }

    private int addHealthPoints(int b) {
        byte playerHp = (byte) (this.getHealthPoint() + b);
        if (playerHp >= 100) {
            this.setHealthPoint((byte) 100);
        } else {
            this.setHealthPoint(playerHp);
        }
        return this.getHealthPoint();
    }

    public int health(HealthPropertie health) throws InterruptedException {
        int helthPointPerSecond =  (health.getHealthPoints() / health.getTimeToUse());
        int hp = health.getHealthPoints();

        while (hp > 0) {
            addHealthPoints(helthPointPerSecond);
            hp -= helthPointPerSecond;
            Thread.sleep(100);
        }

        return this.getHealthPoint();
    }

}
