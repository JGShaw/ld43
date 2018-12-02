package com.ld43.game.state;

public abstract class State {

    public final StateManager stateManager;

    public State(StateManager stateManager){
        this.stateManager = stateManager;
    }

    abstract void create();

    abstract void render();

    abstract void destroy();

}
