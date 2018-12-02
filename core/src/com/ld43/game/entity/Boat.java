package com.ld43.game.entity;

import com.badlogic.ashley.core.Entity;
import com.ld43.game.entity.component.*;
import com.ld43.game.graphics.TextureRegistry;
import com.ld43.game.map.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class Boat {
    public static Entity placeBoat(float x, float y){
        Entity boat = new Entity();
        boat.add(new RenderableComponent(TextureRegistry.getTexture("boat-0")));
        boat.add(new PositionComponent(x, y));
        boat.add(new VelocityComponent(32f, 32f, 32f));
        boat.add(new CollisionComponent(14));

        List<Tile> waypoints = new ArrayList<Tile>();
        boat.add(new RouteComponent(waypoints));
        boat.add(new HealthComponent(100, 100, "boat-0--sunk"));
        return boat;
    }
}
