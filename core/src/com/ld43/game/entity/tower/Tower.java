package com.ld43.game.entity.tower;

import com.badlogic.ashley.core.Entity;
import com.ld43.game.entity.component.*;
import com.ld43.game.entity.projectiles.ProjectileTimer;
import com.ld43.game.entity.projectiles.ProjectileType;
import com.ld43.game.graphics.TextureRegistry;
import java.util.ArrayList;

import com.ld43.game.entity.component.TowerTargetDeciderComponent.TowerBehaviour;
import static com.ld43.game.entity.component.TowerTargetDeciderComponent.TowerBehaviour.RANDOM_TARGET;


public class Tower {

    public static Entity createTower(float posX, float posY) {
        return createTower(posX, posY, 1000, RANDOM_TARGET);
    }

    public static Entity createTower(float posX, float posY, float health, TowerBehaviour behaviour){

        Entity tower = new Entity();

        tower.add(new RenderableComponent(TextureRegistry.getTexture("tower"), 64, 64));
        tower.add(new CollisionComponent(32));
        tower.add(new PositionComponent(posX, posY));

        ArrayList<ProjectileTimer> availableProjectiles = new ArrayList<ProjectileTimer>();
        availableProjectiles.add(new ProjectileTimer(ProjectileType.PROJECTILE_HUGE, 10f, 3f));
        availableProjectiles.add(new ProjectileTimer(ProjectileType.PROJECTILE_LARGE, 6f, 2f));
        availableProjectiles.add(new ProjectileTimer(ProjectileType.PROJECTILE_MEDIUM, 3f, 1f));
        availableProjectiles.add(new ProjectileTimer(ProjectileType.PROJECTILE_SMALL, 0.5f, 0f));
        tower.add(new ProjectileLauncherComponent(availableProjectiles, 0));

        tower.add(new HealthComponent(health, health));
        tower.add(new TowerTargetDeciderComponent(behaviour));
        tower.add(new TowerComponent());

        return tower;

    }

}
