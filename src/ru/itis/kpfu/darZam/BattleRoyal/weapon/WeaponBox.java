package ru.itis.kpfu.darZam.BattleRoyal.weapon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WeaponBox {

    private final Image icon = new ImageIcon
            ("C:\\Users\\danis\\Desktop\\BattleRoyal\\BattleRoyal\\src\\ru\\itis\\kpfu\\darZam\\BattleRoyal\\resources\\image\\box.png").getImage();

    private List<WeaponItem> weaponItems;

    public WeaponBox (){
        this.weaponItems = new ArrayList<>();
    }

    public void addWeapon(WeaponItem weaponItem){
        weaponItems.add(weaponItem);
    }

    public WeaponItem getRandom(){
        int random =(int) (Math.random()*weaponItems.size());
        return weaponItems.get(random);
    }

    public Image getIcon() {
        return icon;
    }
}
