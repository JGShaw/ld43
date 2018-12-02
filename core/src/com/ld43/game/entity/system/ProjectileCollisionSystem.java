package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.*;

public class ProjectileCollisionSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<ProjectileComponent> prm = ComponentMapper.getFor(ProjectileComponent.class);
    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

    public ProjectileCollisionSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class, ProjectileComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);

            ImmutableArray<Entity> boats = getEngine().getEntitiesFor(Family.all(PositionComponent.class, CollisionComponent.class, RouteComponent.class, HealthComponent.class).get());

            for(Entity boat : boats) {

                if(isCollisionBetween(entity, boat)){

                    ProjectileComponent pc = prm.get(entity);
                    HealthComponent boatHealth = hm.get(boat);
                    boatHealth.takeDamage(pc.damage);
                    getEngine().removeEntity(entity);

                }

            }
        }
    }

    private boolean isCollisionBetween(Entity a, Entity b) {

        PositionComponent pa = a.getComponent(PositionComponent.class);
        PositionComponent pb = b.getComponent(PositionComponent.class);

        CollisionComponent ca = a.getComponent(CollisionComponent.class);
        CollisionComponent cb = b.getComponent(CollisionComponent.class);

        return Math.sqrt(Math.pow((pa.x - pb.x), 2) + Math.pow((pa.y - pb.y), 2)) < (ca.radius + cb.radius);

    }
}