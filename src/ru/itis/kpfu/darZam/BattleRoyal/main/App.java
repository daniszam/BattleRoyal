package ru.itis.kpfu.darZam.BattleRoyal.main;

import ru.itis.kpfu.darZam.BattleRoyal.health.Bandage;
import ru.itis.kpfu.darZam.BattleRoyal.player.Player;
import ru.itis.kpfu.darZam.BattleRoyal.weapon.Pistol;

public class App {

    public static void main(String[] args) throws InterruptedException {
        Player player = new Player();
        Player player1 = new Player();
        Pistol pistol = new Pistol();
        Bandage bandage = new Bandage();
        //System.out.println(player.attack(player1));
        player.setWeapon(pistol);
        player1.health(bandage.getHealth());
      //  System.out.println(player.attack(player1));
      //  System.out.println(pistol.getMagazine());
    }
}
