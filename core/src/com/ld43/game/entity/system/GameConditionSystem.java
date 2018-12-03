package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.BoatComponent;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.TowerComponent;
import com.ld43.game.entity.component.VelocityComponent;
import com.ld43.game.state.*;

import java.util.*;

public class GameConditionSystem extends EntitySystem {

    private StateManager stateManager;
    private State.StateType winState;
    private State.StateType loseState;

    public GameConditionSystem(StateManager stateManager, State.StateType loseState, State.StateType winState) {
        this.stateManager = stateManager;
        this.winState = winState;
        this.loseState = loseState;
    }

    public void addedToEngine(Engine engine) {

    }

    public void update(float deltaTime) {

        ImmutableArray<Entity> boats = getEngine().getEntitiesFor(Family.all(BoatComponent.class).get());
        ImmutableArray<Entity> towers = getEngine().getEntitiesFor(Family.all(TowerComponent.class).get());

        List<Entity> boatList = Arrays.asList(boats.toArray());

        if(towers.size() == 0) {
            stateManager.push(generateState(winState, getEngine()));
        } else if(boats.size() == 0) {
            stateManager.push(generateState(loseState, getEngine()));
        }

    }

    private State generateState(State.StateType stateType, Engine engine){

        switch (stateType){
            case WIN:
                return new WinState(stateManager);
            case LOSE:
                return new LoseState(stateManager);
            case PROGRESS:
                return new ProgressState(stateManager);
            case LEVEL_ONE:
                return new LevelOne(stateManager);
            case LEVEL_TWO:
                return new LevelTwo(stateManager, engine);
            case LEVEL_THREE:
                return new LevelThree(stateManager, engine);
        }

        return null;
    }
}