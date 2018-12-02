package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.RenderableComponent;
import com.ld43.game.entity.component.RouteComponent;
import com.ld43.game.entity.component.VelocityComponent;
import com.ld43.game.input.InputHandler;
import com.ld43.game.map.TileMap;
import com.ld43.game.map.tiles.LandTile;
import com.ld43.game.map.tiles.Tile;
import com.ld43.game.math.MathUtils;

public class BoatVelocitySystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private TileMap tileMap;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<RouteComponent> rm = ComponentMapper.getFor(RouteComponent.class);
    private ComponentMapper<RenderableComponent> renm = ComponentMapper.getFor(RenderableComponent.class);

    public BoatVelocitySystem(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class,
                VelocityComponent.class,
                RouteComponent.class,
                RenderableComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            PositionComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);
            RouteComponent route = rm.get(entity);
            RenderableComponent render = renm.get(entity);

            int tileX = InputHandler.tileTouchUpX;
            int tileY = InputHandler.tileTouchUpY;

            if(tileX != -1 && tileY != -1 && InputHandler.boatNumber == i) {
                Tile tile = new LandTile(tileX, tileY, true);
                tileMap.setTile(tileX, tileY, tile);
                route.addToPath(tile);
                InputHandler.resetTileTouchUp();
            }

            route.updateWaypoint(position.x, position.y);
            if(route.complete) {
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