package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.RouteComponent;
import com.ld43.game.entity.component.VelocityComponent;
import com.ld43.game.math.MathUtils;

public class BoatVelocitySystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<RouteComponent> rm = ComponentMapper.getFor(RouteComponent.class);

    public BoatVelocitySystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class, RouteComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            PositionComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);
            RouteComponent route = rm.get(entity);

            route.updateWaypoint(position.x, position.y);

            double[] vector = route.getVector(velocity.maxSpeed);
            velocity.x = (float) vector[0];
            velocity.y = (float) vector[1];
        }
    }
}