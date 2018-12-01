package com.ld43.game.entity.projectiles;

public class ProjectileTimer {

    public String projectileId;
    public float timeout;
    public float timeUntilAvailable;

    public ProjectileTimer(String projectileId, float timeout, float initialTimeout) {
        this.projectileId = projectileId;
        this.timeout = timeout;
        this.timeUntilAvailable = initialTimeout;
    }

    public void update(float deltaTime){
        timeUntilAvailable = timeUntilAvailable <= 0f ? 0f : timeUntilAvailable - deltaTime;
    }

    public boolean isAvailable(){
        return timeUntilAvailable == 0f;
    }

    public void timeout() {
        this.timeUntilAvailable = timeout;
    }

}
