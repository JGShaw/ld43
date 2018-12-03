package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.ld43.game.entity.component.*;

import static com.ld43.game.entity.component.TowerTargetDeciderComponent.MAX_SIGHT_RANGE;

public class SeaMineSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<ProjectileComponent> prm = ComponentMapper.getFor(ProjectileComponent.class);
    private ComponentMapper<SeaMineComponent> smm = ComponentMapper.getFor(SeaMineComponent.class);

    public SeaMineSystem(int priority) { super(priority); }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(SeaMineComponent.class, VelocityComponent.class, PositionComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            VelocityComponent velocity = vm.get(entity);
            PositionComponent pc = pm.get(entity);
            SeaMineComponent seaMine = smm.get(entity);

            if (seaMine.z <= 0) {
                velocity.x = 0;
                velocity.y = 0;

                for(Entity boat: getEngine().getEntitiesFor(Family.all(BoatComponent.class).get())){

                    PositionComponent boatPos = pm.get(boat);

                    float distanceBetween = (float)Math.sqrt(Math.pow(pc.x - boatPos.x, 2) + Math.pow(pc.y - boatPos.y, 2));

                    if(distanceBetween < 64){

                        getEngine().removeEntity(entity);
                        boat.getComponent(HealthComponent.class).takeDamage(1000);
                        break;
                    }


                }

            } else {
                entity.getComponent(RenderableComponent.class).rotation += MathUtils.PI2 * deltaTime;
                seaMine.zVel += seaMine.gravity * deltaTime;
                seaMine.z += seaMine.zVel * deltaTime;
            }

        }
    }
}