package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.ld43.game.entity.projectiles.ProjectileTimer;

import java.util.ArrayList;

public class ProjectileLauncherComponent implements Component {

    public ArrayList<ProjectileTimer> availableProjectiles;

    public ProjectileLauncherComponent(){
        availableProjectiles = new ArrayList<ProjectileTimer>();
        availableProjectiles.add(new ProjectileTimer("0", 0.1f, 2f));
    }

    public void updateTimeouts(final float deltaTime){

        for (ProjectileTimer projectileTimer : availableProjectiles) {
            projectileTimer.update(deltaTime);
        }

    }


    public String getFirstAvailable() {

        String projectile = null;

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
