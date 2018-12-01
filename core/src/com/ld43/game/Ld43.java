package com.ld43.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.RenderableComponent;
import com.ld43.game.entity.component.RouteComponent;
import com.ld43.game.entity.component.VelocityComponent;
import com.ld43.game.entity.system.MovementSystem;
import com.ld43.game.map.TileMap;
import com.ld43.game.map.tiles.Tile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Ld43 extends ApplicationAdapter {
	SpriteBatch batch;
	TileMap map;

	// Ashley ECS
	private Engine engine = new Engine();
	private MovementSystem ms = new MovementSystem();
	private Family renderable = Family.all(RenderableComponent.class, PositionComponent.class).get();
	private Entity boat = new Entity();
	private Texture boatTexture;

	private OrthographicCamera camera;
	
	@Override
	public void create () {
		map = TileMap.circleMap(31, 31, 14, 2);
		batch = new SpriteBatch();

		camera = new OrthographicCamera(31*32, 31*32);

		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

		boatTexture = new Texture("tiles/boat.png");
		boat.add(new RenderableComponent(boatTexture));
		boat.add(new PositionComponent(16f, 16f));
		boat.add(new VelocityComponent(32f, 32f, 32f));

		List<Tile> tiles = Arrays.asList(map.getTiles());
		boat.add(new RouteComponent(tiles));
		engine.addEntity(boat);

		engine.addSystem(ms);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		camera.update();
		engine.update(Gdx.graphics.getDeltaTime());


		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		map.render(batch);

		ImmutableArray<Entity> renderables = engine.getEntitiesFor(renderable);

		for(Entity entity: renderables){
			RenderableComponent rc = entity.getComponent(RenderableComponent.class);
			PositionComponent pc = entity.getComponent(PositionComponent.class);

			batch.draw(rc.texture, pc.x - rc.width / 2, pc.y - rc.height / 2, rc.width, rc.height);

		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
