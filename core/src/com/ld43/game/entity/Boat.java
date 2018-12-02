package com.ld43.game.entity;

import com.badlogic.ashley.core.Entity;
import com.ld43.game.entity.component.*;
import com.ld43.game.entity.projectiles.ProjectileTimer;
import com.ld43.game.entity.projectiles.ProjectileType;
import com.ld43.game.graphics.TextureRegistry;
import com.ld43.game.map.tiles.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Boat {
    public static Entity placeBoat(float x, float y, float r){
        Entity boat = new Entity();
        boat.add(new RenderableComponent(TextureRegistry.getTexture("boat-0"), 32, 32, r));
        boat.add(new PositionComponent(x, y));
        boat.add(new VelocityComponent(0f, 0f, 32f));
        boat.add(new CollisionComponent(14));
        boat.add(new FocusableComponent(false, 16));
        boat.add(new BoatComponent());

        List<Tile> waypoints = new ArrayList<Tile>();
        boat.add(new RouteComponent(waypoints));
        boat.add(new HealthComponent(100, 100, "boat-0--sunk"));

        boat.add(new ProjectileLauncherComponent(
                Collections.singletonList(new ProjectileTimer(ProjectileType.PROJECTILE_SMALL, 0.5f,0 ))
            )
        );

        return boat;
    }

    public static Entity placeBoat(float x, float y) {
        return placeBoat(x, y, 0);
    }
}
