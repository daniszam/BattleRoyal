package ru.itis.kpfu.darZam.BattleRoyal.player;


import ru.itis.kpfu.darZam.BattleRoyal.health.HealthPropertie;
import ru.itis.kpfu.darZam.BattleRoyal.weapon.WeaponItem;

import javax.swing.*;
import java.awt.*;

public class Player {

    private byte healthPoint;
    private byte damage;
    private WeaponItem weapon;
    private Image icon;

    public Player(){
        healthPoint = 100;
        damage = 5;
        icon = new ImageIcon("/Users/danis_zam/IdeaProjects/BattleRoyal/src/ru/itis/kpfu/darZam/BattleRoyal/resources/image/dino.png").getImage();

    }

    public Image getIcon() {
        return icon;
    }

    public byte getHealthPoint() {
        return healthPoint;
    }

    public byte getDamage() {
        return damage;
    }

    public void setHealthPoint(byte healthPoint) {
        if (healthPoint>100) {
            this.healthPoint = 100;
        } else{
            this.healthPoint = healthPoint;
        }
    }

    public void setDamage(byte damage) {
        this.damage = damage;
    }

    public void attack(){
        if (weapon != null) {
            weapon.shot();
        }
    }

    public WeaponItem getWeapon() {
        return weapon;
    }

    public void setWeapon(WeaponItem weapon) {
        this.weapon = weapon;
    }

    private byte addHealthPoints(byte b){
        byte playerHp =(byte) (this.getHealthPoint() + b);
        if (playerHp >=100){
            this.setHealthPoint((byte)100);
        }else {
            this.setHealthPoint(playerHp);
        }
        return this.getHealthPoint();
    }

    public byte health(HealthPropertie health) throws InterruptedException {
        byte helthPointPerSecond =(byte) ( health.getHealthPoints() / health.getTimeToUse() );
        byte hp = health.getHealthPoints();

        while (hp>0){
            addHealthPoints(helthPointPerSecond);
            hp -= helthPointPerSecond;
            Thread.sleep(100);
        }

        return this.getHealthPoint();
    }

}
