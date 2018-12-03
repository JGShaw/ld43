package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.ld43.game.entity.projectiles.ProjectileTimer;
import com.ld43.game.entity.projectiles.ProjectileType;

import java.util.List;

public class ProjectileLauncherComponent implements Component {

    public List<ProjectileTimer> availableProjectiles;
    public float aimVariance;
    public boolean enabled;

    public ProjectileLauncherComponent(List<ProjectileTimer> availableProjectiles, float aimVariance){
        this.availableProjectiles = availableProjectiles;
        this.aimVariance = aimVariance;
    }

    public void updateTimeouts(final float deltaTime){

        for (ProjectileTimer projectileTimer : availableProjectiles) {
            projectileTimer.update(deltaTime);
        }

    }

    public ProjectileType getFirstAvailable() {

        ProjectileType projectile = null;

        for (ProjectileTimer timer : availableProjectiles) {
            if(timer.isAvailable()){

                projectile = timer.projectileId;
                timer.timeout();
                break;

            }
        }

        return projectile;

    }

    public void setAvailable(ProjectileType projectileId) {

        for (ProjectileTimer projectile: availableProjectiles) {
            if(projectile.projectileId == projectileId){
                projectile.timeUntilAvailable = 0;
                return;
            }
        }

    }
}
