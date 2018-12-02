package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.*;
import com.ld43.game.input.InputHandler;
import com.ld43.game.map.TileMap;
import com.ld43.game.math.MathUtils;

public class BoatVelocitySystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private TileMap tileMap;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<RouteComponent> rm = ComponentMapper.getFor(RouteComponent.class);
    private ComponentMapper<RenderableComponent> renm = ComponentMapper.getFor(RenderableComponent.class);
    private ComponentMapper<FocusableComponent> fm = ComponentMapper.getFor(FocusableComponent.class);

    public BoatVelocitySystem(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class,
                VelocityComponent.class,
                RouteComponent.class,
                RenderableComponent.class,
                FocusableComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            PositionComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);
            RouteComponent route = rm.get(entity);
            RenderableComponent render = renm.get(entity);
            FocusableComponent fc = fm.get(entity);

            int tileX = InputHandler.tileTouchUpX;
            int tileY = InputHandler.tileTouchUpY;

            if(tileX != -1 && tileY != -1 && fc.focused) {
                route.addToPath(tileMap.getTile(tileX, tileY));
                InputHandler.resetTileTouchDown();
            }

            route.updateWaypoint(position.x, position.y);
            if(route.isComplete()) {
                velocity.x = 0;
                velocity.y = 0;
            } else {
                double angle = route.getAngle(position.x, position.y);
                double[] vector = MathUtils.movementVectors(velocity.maxSpeed, angle);
                velocity.x = (float) vector[0];
                velocity.y = (float) vector[1];
                render.rotation = (float) angle;
            }
        }
    }
}