package ru.itis.kpfu.darZam.BattleRoyal.weapon;


import javax.swing.*;
import java.awt.*;

public class Pistol extends WeaponItem {

    private static final int DAMAGE = 7;
    private static final int MAGAZINE = 15;
    private static final Image ICON = new ImageIcon
            ("/Users/danis_zam/IdeaProjects/BattleRoyal/src/ru/itis/kpfu/darZam/BattleRoyal/resources/image/pistol.png").getImage();


    public Pistol(){
        super(DAMAGE, MAGAZINE, ICON);
    }


}
