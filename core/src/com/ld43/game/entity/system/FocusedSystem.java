package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.FocusableComponent;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.VelocityComponent;
import com.ld43.game.input.InputHandler;

public class FocusedSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<FocusableComponent> fm = ComponentMapper.getFor(FocusableComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public FocusedSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(FocusableComponent.class).get());
    }

    public void update(float deltaTime) {

        float touchDownX = InputHandler.touchDownX;
        float touchDownY = InputHandler.touchDownY;

        if(touchDownX == -1 && touchDownY == -1) {
            return;
        }

        Entity focused = null;

        for(Entity entity: entities){
            FocusableComponent fc = fm.get(entity);
            if(fc.focused){
                focused = entity;
                break;
            }
        }

        for(Entity entity: entities){
            FocusableComponent fc = fm.get(entity);
            fc.focused = false;
        }

        for(Entity entity : entities) {
            PositionComponent pc = pm.get(entity);
            FocusableComponent fc = fm.get(entity);

            double distance = Math.sqrt(Math.pow(pc.x - touchDownX, 2) + Math.pow(pc.y - touchDownY, 2));
            if(distance < fc.radius) {
                focused = entity;
                break;
            }
        }

        if(focused != null)
            fm.get(focused).focused = true;

        InputHandler.resetTouchDown();
    }
}