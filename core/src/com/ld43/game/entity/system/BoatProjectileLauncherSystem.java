package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.ld43.game.entity.component.BoatComponent;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.ProjectileLauncherComponent;
import com.ld43.game.entity.component.TowerComponent;
import com.ld43.game.entity.projectiles.ProjectileBuilder;
import com.ld43.game.entity.projectiles.ProjectileType;

public class BoatProjectileLauncherSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<ProjectileLauncherComponent> plm = ComponentMapper.getFor(ProjectileLauncherComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private Family towerFamily = Family.all(TowerComponent.class, PositionComponent.class).get();

    public BoatProjectileLauncherSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ProjectileLauncherComponent.class, PositionComponent.class, BoatComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            ProjectileLauncherComponent projectileLauncher = plm.get(entity);
            PositionComponent position = pm.get(entity);

            projectileLauncher.updateTimeouts(deltaTime);

            ProjectileType projectileId = projectileLauncher.getFirstAvailable();

            if(projectileId != null) {

                ImmutableArray<Entity> towers = getEngine().getEntitiesFor(towerFamily);

                if(towers.size() == 0) return;

                Entity tower = towers.first();
                float angle;
                if(tower != null) {
                    PositionComponent towerPosition = pm.get(tower);
                    angle = (float) com.ld43.game.math.MathUtils.angleBetweenPoints(position.x, position.y, towerPosition.x, towerPosition.y);
                } else {
                    angle = MathUtils.random(0, 2 * MathUtils.PI);
                }

                Entity p = ProjectileBuilder.projectile(projectileId, false, position.x, position.y, angle);

                getEngine().addEntity(p);
            }
        }
    }
}