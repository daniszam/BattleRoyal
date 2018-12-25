package ru.itis.kpfu.darZam.BattleRoyal.weapon;

import ru.itis.kpfu.darZam.BattleRoyal.player.Player;

import javax.swing.*;
import java.awt.*;

public class Bullet {

    private Image icon;
    private int damage;

    public Bullet(int damage){
        this.damage = damage;
        this.icon = Icon.RIGHT.icon;
    }


    public Image getIcon() {
        return icon;
    }

    public void setImage(Icon icon){
        this.icon = icon.icon;
    }

    public int hitPlayer(Player player){
        player.setHealthPoint(player.getHealthPoint()-damage);
        return player.getHealthPoint();
    }

    public int getDamage() {
        return damage;
    }

    public enum Icon{
        UP("-up"),
        DOWN("-down"),
        LEFT("-left"),
        RIGHT("");

        private static final String standart = "C:\\Users\\danis\\Desktop\\BattleRoyal\\BattleRoyal\\" +
                "src\\ru\\itis\\kpfu\\darZam\\BattleRoyal\\resources\\image\\bullet";
        private static final String format = ".png";
        private Image icon;

        private Icon (String icon){
            this.icon = new ImageIcon(standart.concat(icon).concat(format)).getImage();
        }
    }
}
