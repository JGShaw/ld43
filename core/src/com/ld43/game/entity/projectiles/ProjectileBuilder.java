package com.ld43.game.entity.projectiles;

import com.badlogic.ashley.core.Entity;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.RenderableComponent;
import com.ld43.game.entity.component.VelocityComponent;
import com.ld43.game.graphics.TextureRegistry;

public class ProjectileBuilder {

    public static Entity projectile(String projectileId, float xPos, float yPos, float xVel, float yVel){

        Entity entity = new Entity();

        entity.add(new PositionComponent(xPos, yPos));
        entity.add(new VelocityComponent(xVel, yVel));
        entity.add(new RenderableComponent(TextureRegistry.getTexture("projectile"), 4, 4));

        return entity;
    }


}
