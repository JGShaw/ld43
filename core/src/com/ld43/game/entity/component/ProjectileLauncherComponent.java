package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.ld43.game.entity.projectiles.ProjectileTimer;
import com.ld43.game.entity.projectiles.ProjectileType;

import java.util.ArrayList;

public class ProjectileLauncherComponent implements Component {

    public ArrayList<ProjectileTimer> availableProjectiles;

    public ProjectileLauncherComponent(){
        availableProjectiles = new ArrayList<ProjectileTimer>();
        availableProjectiles.add(new ProjectileTimer(ProjectileType.PROJECTILE_HUGE, 10f, 3f));
        availableProjectiles.add(new ProjectileTimer(ProjectileType.PROJECTILE_LARGE, 6f, 2f));
        availableProjectiles.add(new ProjectileTimer(ProjectileType.PROJECTILE_MEDIUM, 3f, 1f));
        availableProjectiles.add(new ProjectileTimer(ProjectileType.PROJECTILE_SMALL, 0.5f, 0f));
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
}
