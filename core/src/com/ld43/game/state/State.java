package com.ld43.game.state;

public abstract class State {

    public enum StateType {
        LEVEL_ONE,
        LEVEL_TWO,
        LEVEL_THREE,
        WIN,
        LOSE,
        PROGRESS
    }

    public final StateManager stateManager;

    public State(StateManager stateManager){
        this.stateManager = stateManager;
    }

    abstract void create();

    abstract void render();

    abstract void destroy();

}
