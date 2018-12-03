package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.ProjectileLauncherComponent;
import com.ld43.game.entity.projectiles.ProjectileBuilder;
import com.ld43.game.entity.projectiles.ProjectileType;
import com.ld43.game.entity.component.TowerTargetDeciderComponent;
import com.ld43.game.input.InputHandler;

public class ProjectileLauncherSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<ProjectileLauncherComponent> plm = ComponentMapper.getFor(ProjectileLauncherComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<TowerTargetDeciderComponent> tm = ComponentMapper.getFor(TowerTargetDeciderComponent.class);

    public ProjectileLauncherSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ProjectileLauncherComponent.class, PositionComponent.class, TowerTargetDeciderComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            ProjectileLauncherComponent projectileLauncher = plm.get(entity);
            if(!InputHandler.running) continue;

            PositionComponent position = pm.get(entity);

            TowerTargetDeciderComponent targetDecider = tm.get(entity);

            projectileLauncher.updateTimeouts(deltaTime);

            ProjectileType projectileId = projectileLauncher.getFirstAvailable();

            if(projectileId != null) {

                Entity target = targetDecider.getTarget(position.x, position.y, getEngine());

                if(target != null) {
                    Entity p = ProjectileBuilder.projectile(projectileId, true, position.x, position.y, target);
                    getEngine().addEntity(p);
                }
            }
        }
    }
}