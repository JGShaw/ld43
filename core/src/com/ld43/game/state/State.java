package com.ld43.game.state;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ld43.game.entity.component.*;

public abstract class State {

    public enum StateType {
        LEVEL_ONE,
        LEVEL_TWO,
        LEVEL_THREE,
        WIN,
        LOSE,
        PROGRESS
    }

    public final Engine engine;
    public final StateManager stateManager;

    public ShapeRenderer shapeRenderer;
    public SpriteBatch batch;


    Family renderable = Family.all(RenderableComponent.class, PositionComponent.class).exclude(UnderwaterComponent.class, SeaMineComponent.class).get();
    Family renderableUnderwater = Family.all(RenderableComponent.class, PositionComponent.class, UnderwaterComponent.class).exclude(SeaMineComponent.class).get();
    Family hasHealthBar = Family.all(HealthComponent.class, RenderableComponent.class, PositionComponent.class).get();
    Family canBeFocused = Family.all(FocusableComponent.class, RenderableComponent.class, PositionComponent.class).get();
    Family isBoat = Family.all(BoatComponent.class, RenderableComponent.class, HealthComponent.class).get();
    Family isProjectile = Family.all(ProjectileComponent.class).get();

    public State(StateManager stateManager, Engine engine){
        this.stateManager = stateManager;
        this.engine = engine;
    }

    public void create(){
        batch = new SpriteBatch(2000);
        shapeRenderer = new ShapeRenderer();
    }

    abstract void render();

    abstract void destroy();

}
