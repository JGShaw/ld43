package com.ld43.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.ld43.game.graphics.TextureRegistry;
import com.ld43.game.input.InputHandler;
import com.ld43.game.state.LevelOne;
import com.ld43.game.state.StateManager;

public class Ld43 extends ApplicationAdapter {

	private StateManager stateManager;

	@Override
	public void create () {
        TextureRegistry.loadTextures();

        stateManager = new StateManager();
        stateManager.push(new LevelOne(stateManager));

		InputHandler inputProcessor = new InputHandler();
		Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(68/255f, 157/255f,  223/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stateManager.render();

	}

	@Override
	public void dispose () {
		stateManager.destroy();
	}
}
