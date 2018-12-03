package com.ld43.game.entity.projectiles;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.ld43.game.entity.component.*;
import com.ld43.game.graphics.TextureRegistry;

public class ProjectileBuilder {

    public static Entity projectile(ProjectileType projectileId, boolean enemy, float xPos, float yPos, float angle){

        Entity entity = new Entity();
        entity.add(new PositionComponent(xPos, yPos));

        float xVelComp = MathUtils.sin(angle);
        float yVelComp = MathUtils.cos(angle);

        switch (projectileId) {
            case PROJECTILE_SMALL:
                entity.add(new ProjectileComponent(10, enemy));
                entity.add(new VelocityComponent(xVelComp * 200, yVelComp * 200, 0));
                entity.add(new CollisionComponent(2));

                String texture = enemy ? "projectile-enemy-small" : "projectile-friendly-small";
                entity.add(new RenderableComponent(TextureRegistry.getTexture(texture), 4, 4, angle));
                break;
            case PROJECTILE_MEDIUM:
                entity.add(new ProjectileComponent(15, enemy));
                entity.add(new VelocityComponent(xVelComp * 150, yVelComp * 150, 0));
                entity.add(new CollisionComponent(3));
                entity.add(new RenderableComponent(TextureRegistry.getTexture("projectile--medium"), 6, 6, angle));
                break;
            case PROJECTILE_LARGE:
                entity.add(new ProjectileComponent(30, enemy));
                entity.add(new VelocityComponent(xVelComp * 100, yVelComp * 100, 0));
                entity.add(new CollisionComponent(4));
                entity.add(new RenderableComponent(TextureRegistry.getTexture("projectile--large"), 8, 8, angle));
                break;
            case PROJECTILE_HUGE:
                entity.add(new ProjectileComponent(50, enemy));
                entity.add(new VelocityComponent(xVelComp * 50, yVelComp * 50, 0));
                entity.add(new CollisionComponent(14));
                entity.add(new RenderableComponent(TextureRegistry.getTexture("projectile--huge"), 32, 32, angle));
                break;
            default:
                return null;
        }

        return entity;
    }


}
