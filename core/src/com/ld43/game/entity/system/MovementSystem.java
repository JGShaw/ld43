package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.*;
import com.ld43.game.entity.component.ProjectileComponent;

import static com.ld43.game.entity.component.TowerTargetDeciderComponent.MAX_SIGHT_RANGE;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<ProjectileComponent> prm = ComponentMapper.getFor(ProjectileComponent.class);

    public MovementSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            PositionComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);

            position.x += velocity.x * deltaTime;
            position.y += velocity.y * deltaTime;
        }

        checkParticlesShouldDie();
    }

    private void checkParticlesShouldDie() {

        ImmutableArray<Entity> projectiles = getEngine().getEntitiesFor(Family.all(PositionComponent.class, ProjectileComponent.class).get());
        ImmutableArray<Entity> towers = getEngine().getEntitiesFor(Family.all(TowerComponent.class, PositionComponent.class).get());

        for(Entity projectile: projectiles){

            PositionComponent pos = pm.get(projectile);

            if(towers.size() == 0) continue;
            Entity tower = towers.first();
            PositionComponent towerPos = pm.get(tower);

            double distanceBetween = Math.sqrt(Math.pow(pos.x - towerPos.x, 2) + Math.pow(pos.y - towerPos.y, 2));
            if(distanceBetween > MAX_SIGHT_RANGE - 5){
               getEngine().removeEntity(projectile);
            }


        }

    }
}