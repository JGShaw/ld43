package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.*;

import java.text.CollationElementIterator;

public class ProjectileCollisionSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<ProjectileComponent> prm = ComponentMapper.getFor(ProjectileComponent.class);
    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

    public ProjectileCollisionSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class, ProjectileComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);

            ProjectileComponent pc = prm.get(entity);

            if(pc.enemy){
                ImmutableArray<Entity> boats = getEngine().getEntitiesFor(Family.all(PositionComponent.class, CollisionComponent.class, RouteComponent.class, HealthComponent.class).get());

                for(Entity boat : boats) {

                    if(isCollisionBetween(entity, boat)){
                        HealthComponent boatHealth = hm.get(boat);
                        boatHealth.takeDamage(pc.damage);
                        getEngine().removeEntity(entity);
                    }

                }
            } else {
                ImmutableArray<Entity> towers = getEngine().getEntitiesFor(Family.all(TowerComponent.class, CollisionComponent.class).get());

                for(Entity tower : towers) {

                    if(isCollisionBetween(entity, tower)){
                        HealthComponent boatHealth = hm.get(tower);
                        boatHealth.takeDamage(pc.damage);
                        getEngine().removeEntity(entity);
                    }
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