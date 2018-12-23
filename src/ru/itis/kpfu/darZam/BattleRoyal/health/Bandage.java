package ru.itis.kpfu.darZam.BattleRoyal.health;

public class Bandage implements Health {

    private static final byte HEALTH_POINTS = 30;
    private static final byte TIME_TO_USE = 5;

    @Override
    public HealthPropertie getHealth() {
        return new HealthPropertie(HEALTH_POINTS, TIME_TO_USE);
    }
}
