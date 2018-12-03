package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.BoatComponent;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.TowerComponent;
import com.ld43.game.entity.component.VelocityComponent;
import com.ld43.game.input.InputHandler;
import com.ld43.game.state.*;

import java.util.*;

public class GameConditionSystem extends EntitySystem {

    private StateManager stateManager;
    private State.StateType winState;
    private State.StateType loseState;
    private State.StateType postProgressState;
    private Condition condition;

    public GameConditionSystem(StateManager stateManager, Condition condition, State.StateType loseState, State.StateType winState, State.StateType postProgressState) {
        this.stateManager = stateManager;
        this.winState = winState;
        this.loseState = loseState;
        this.postProgressState = postProgressState;
        this.condition = condition;
    }

    public void addedToEngine(Engine engine) {

    }

    public void update(float deltaTime) {

        switch (condition){
            case TOWER_OR_BOATS:
                ImmutableArray<Entity> boats = getEngine().getEntitiesFor(Family.all(BoatComponent.class).get());
                ImmutableArray<Entity> towers = getEngine().getEntitiesFor(Family.all(TowerComponent.class).get());

                if(towers.size() == 0) {
                    stateManager.push(generateState(winState, getEngine()));
                } else if(boats.size() == 0) {
                    stateManager.push(generateState(loseState, getEngine()));
                }
                break;
            case NONE:
                if(InputHandler.nextState){
                    stateManager.push(generateState(winState, getEngine()));
                }
                break;
            default:
        }
    }

    private State generateState(State.StateType stateType, Engine engine){

        switch (stateType){
            case WIN:
                return new WinState(stateManager, engine);
            case LOSE:
                return new LoseState(stateManager, engine);
            case PROGRESS:
                return new ProgressState(stateManager, engine, postProgressState);
            case LEVEL_ONE:
                return new LevelOne(stateManager);
            case LEVEL_TWO:
                return new LevelTwo(stateManager, engine);
            case LEVEL_THREE:
                return new LevelThree(stateManager, engine);
        }

        return null;
    }

    public enum Condition {
        NONE,
        TOWER_OR_BOATS
    }
}