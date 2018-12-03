package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.*;
import com.ld43.game.math.MathUtils;

import static com.ld43.game.entity.component.TowerTargetDeciderComponent.MAX_SIGHT_RANGE;

public class HomingSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<HomingComponent> hm = ComponentMapper.getFor(HomingComponent.class);
    private ComponentMapper<RenderableComponent> rm = ComponentMapper.getFor(RenderableComponent.class);

    public HomingSystem(int priority) { super(priority); }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(HomingComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            PositionComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);
            HomingComponent homing = hm.get(entity);
            RenderableComponent renderer = rm.get(entity);

            Entity target = homing.target;
            float targetX = pm.get(target).x;
            float targetY = pm.get(target).y;

            float currentX = position.x;
            float currentY = position.y;

            double angle = MathUtils.angleBetweenPoints(currentX, currentY, targetX, targetY);
            renderer.rotation = (float) angle;
            double[] vector = MathUtils.movementVectors(velocity.maxSpeed, angle);
            velocity.x = (float) vector[0];
            velocity.y = (float) vector[1];
        }
    }
}