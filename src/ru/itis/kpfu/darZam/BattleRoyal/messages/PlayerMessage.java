package ru.itis.kpfu.darZam.BattleRoyal.messages;

import ru.itis.kpfu.darZam.BattleRoyal.player.MovingPlayer;
import ru.itis.kpfu.darZam.BattleRoyal.player.Player;

public class PlayerMessage {

    public static MovingPlayer getPlayer(String[] response){
        int positionX = Integer.parseInt(response[0]);
        int positionY = Integer.parseInt(response[1]);
        MovingPlayer player = new MovingPlayer(new Player());
        player.setPositionX(positionX);
        player.setPositionY(positionY);
        return player;
    }
}
