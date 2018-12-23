package ru.itis.kpfu.darZam.BattleRoyal.health;

public class HealthPropertie {

    private byte healthPoints;
    private byte timeToUse;

    public byte getHealthPoints() {
        return healthPoints;
    }

    public byte getTimeToUse() {
        return timeToUse;
    }

    public void setHealthPoints(byte healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setTimeToUse(byte timeToUse) {
        this.timeToUse = timeToUse;
    }

    public HealthPropertie(byte healthPoints, byte timeToUse){
        this.healthPoints = healthPoints;
        this.timeToUse = timeToUse;
    }
}
