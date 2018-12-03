package com.ld43.game.state;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class WinState extends State {

    public WinState(StateManager stateManager, Engine engine) {
        super(stateManager, engine);
    }

    @Override
    public void create() {

    }

    @Override
    void render() {
        Gdx.gl.glClearColor(0, 1,  0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    void destroy() {

    }

}
