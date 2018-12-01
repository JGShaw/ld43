package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.ProjectileLauncherComponent;
import com.ld43.game.entity.component.VelocityComponent;
import com.ld43.game.entity.projectiles.ProjectileBuilder;
import com.ld43.game.entity.projectiles.ProjectileTimer;

import javax.swing.text.Position;

public class ProjectileLauncherSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<ProjectileLauncherComponent> plm = ComponentMapper.getFor(ProjectileLauncherComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public ProjectileLauncherSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ProjectileLauncherComponent.class, PositionComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            ProjectileLauncherComponent projectileLauncher = plm.get(entity);
            PositionComponent position = pm.get(entity);

            projectileLauncher.updateTimeouts(deltaTime);

            String projectileId = projectileLauncher.getFirstAvailable();

            if(projectileId != null) {
                float angle = MathUtils.random(360f);
                float xVel = MathUtils.cosDeg(angle) * 200f;
                float yVel = MathUtils.sinDeg(angle) * 200f;
                Entity p = ProjectileBuilder.projectile(projectileId, position.x, position.y, xVel, yVel);

                getEngine().addEntity(p);
            }
        }
    }
}