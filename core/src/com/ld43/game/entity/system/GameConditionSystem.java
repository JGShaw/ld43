package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.BoatComponent;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.TowerComponent;
import com.ld43.game.entity.component.VelocityComponent;
import com.ld43.game.state.State;
import com.ld43.game.state.StateManager;
import com.ld43.game.state.WinState;

public class GameConditionSystem extends EntitySystem {

    private StateManager stateManager;
    private State winState;
    private State loseState;

    private ImmutableArray<Entity> boats;
    private ImmutableArray<Entity> towers;

    public GameConditionSystem(StateManager stateManager, State loseState, State winState) {
        this.stateManager = stateManager;
        this.winState = winState;
        this.loseState = loseState;
    }

    public void addedToEngine(Engine engine) {

    }

    public void update(float deltaTime) {

        boats = getEngine().getEntitiesFor(Family.all(BoatComponent.class).get());
        towers = getEngine().getEntitiesFor(Family.all(TowerComponent.class).get());

        if(towers.size() == 0) {
            stateManager.push(winState);
        } else if(boats.size() == 0) {
            stateManager.push(loseState);
        }

    }
}