package ru.itis.kpfu.darZam.BattleRoyal.weapon;

import ru.itis.kpfu.darZam.BattleRoyal.player.Player;

import java.awt.*;

public abstract class WeaponItem implements Weapon {

    private int damage;
    private int magazine;
    private Image weaponIcon;

    protected WeaponItem(int damage, int magazine, Image weaponIcon) {
        this.damage = damage;
        this.magazine = magazine;
        this.weaponIcon = weaponIcon;
    }

    @Override
    public int shot( ) {
        magazine --;
        return damage;
    }

    public int getMagazine(){
        return magazine;
    }

    public Image getWeaponIcon() {
        return weaponIcon;
    }
}
