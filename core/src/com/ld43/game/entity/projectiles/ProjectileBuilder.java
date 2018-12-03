package com.ld43.game.entity.projectiles;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.ld43.game.entity.component.*;
import com.ld43.game.graphics.TextureRegistry;

public class ProjectileBuilder {

    public static Entity projectile(ProjectileType projectileId, boolean enemy, float xPos, float yPos, Entity target){
        return projectile(projectileId, enemy, xPos, yPos, target, 0);
    }

    public static Entity projectile(ProjectileType projectileId, boolean enemy, float xPos, float yPos, Entity target, float variation){

        Entity entity = new Entity();
        entity.add(new PositionComponent(xPos, yPos));

        PositionComponent targetPos = target.getComponent(PositionComponent.class);
        Float angle = (float)com.ld43.game.math.MathUtils.angleBetweenPoints(xPos, yPos, targetPos.x, targetPos.y) + variation;

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
            case PROJECTILE_HOMING:
                entity.add(new ProjectileComponent(75, enemy));
                entity.add(new VelocityComponent(xVelComp * 40, yVelComp * 40, 40));
                entity.add(new CollisionComponent(14));
                entity.add(new HomingComponent(target));
                entity.add(new RenderableComponent(TextureRegistry.getTexture("projectile--homing"), 32, 32, angle));
                break;
            case PROJECTILE_SEA_MINE:
                angle = MathUtils.random(0, MathUtils.PI2);
                float speed = MathUtils.random(75f, 95f);
                xVelComp = MathUtils.sin(angle);
                yVelComp = MathUtils.cos(angle);
                entity.add(new VelocityComponent(xVelComp * speed, yVelComp * speed, speed));
                entity.add(new CollisionComponent(14));
                entity.add(new SeaMineComponent(0.1f, 0.3f, -0.15f));
                entity.add(new RenderableComponent(TextureRegistry.getTexture("projectile--sea-mine"), 32, 32, angle));
                break;
            default:
                return null;
        }

        return entity;
    }


}
