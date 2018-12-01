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
import com.badlogic.gdx.math.MathUtils;
import com.ld43.game.entity.component.*;
import com.ld43.game.entity.system.MovementSystem;
import com.ld43.game.entity.system.ProjectileLauncherSystem;
import com.ld43.game.graphics.TextureRegistry;
import com.ld43.game.map.TileMap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Ld43 extends ApplicationAdapter {
	SpriteBatch batch;
	TileMap map;

	// Ashley ECS
	private Engine engine = new Engine();
	private MovementSystem ms = new MovementSystem();
	private ProjectileLauncherSystem pls = new ProjectileLauncherSystem();
	private Family renderable = Family.all(RenderableComponent.class, PositionComponent.class).get();

	private Entity boat = new Entity();
	private Texture boatTexture;

    private Entity projectile = new Entity();
    private Texture projectileTexture;

    private Entity tower = new Entity();

    private OrthographicCamera camera;
	
	@Override
	public void create () {
		map = TileMap.circleMap(31, 31, 14, 2);
		batch = new SpriteBatch();

		camera = new OrthographicCamera(31*32, 31*32);

		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

		TextureRegistry.loadTextures();

		boatTexture = new Texture("tiles/boat.png");
		boat.add(new RenderableComponent(boatTexture));
		boat.add(new PositionComponent(32f, 32f));
		boat.add(new VelocityComponent(0f, 0f));
		boat.add(new CollisionBoxComponent(32f, 32f));
		engine.addEntity(boat);

		projectileTexture = new Texture("entities/projectiles/0.png");
		projectile.add(new RenderableComponent(projectileTexture, 4, 4));
		projectile.add(new PositionComponent(32f, 100f));
		projectile.add(new VelocityComponent(0f, -100f));
		projectile.add(new CollisionBoxComponent(4f, 4f));
		engine.addEntity(projectile);

		tower.add(new RenderableComponent(projectileTexture, 64, 64));
		tower.add(new PositionComponent(496f, 496f));
		tower.add(new ProjectileLauncherComponent());
		engine.addEntity(tower);

		engine.addSystem(ms);
		engine.addSystem(pls);

		Collections.reverse(new ArrayList<String>());

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
