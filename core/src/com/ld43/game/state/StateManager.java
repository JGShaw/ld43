package com.ld43.game.state;

import java.util.Stack;

public class StateManager{

    private Stack<State> stateStack = new Stack<State>();

    public void create() {

    }

    public void render() {

        stateStack.peek().render();

    }

    public void destroy() {

        while (!stateStack.isEmpty()) {
            stateStack.pop().destroy();
        }

    }

    public void push(State state) {
        state.create();
        stateStack.push(state);
    }
}
