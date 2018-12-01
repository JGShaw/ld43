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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.ld43.game.entity.component.*;
import com.ld43.game.entity.system.BoatVelocitySystem;
import com.ld43.game.entity.system.MovementSystem;
import com.ld43.game.entity.system.ProjectileLauncherSystem;
import com.ld43.game.graphics.TextureRegistry;
import com.ld43.game.input.RoutePlanner;
import com.ld43.game.map.TileMap;
import com.ld43.game.map.tiles.Tile;
import com.ld43.game.map.tiles.WaterTile;

import java.util.ArrayList;
import java.util.List;

public class Ld43 extends ApplicationAdapter {
	public static final int NUM_OF_TILES = 31;

	SpriteBatch batch;
	TileMap map;

	// Ashley ECS
	private Engine engine = new Engine();
	private MovementSystem ms = new MovementSystem();
	private BoatVelocitySystem bvs = new BoatVelocitySystem();
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
        TextureRegistry.loadTextures();

		map = TileMap.circleMap(NUM_OF_TILES, NUM_OF_TILES, 14, 2);
		batch = new SpriteBatch();


		camera = new OrthographicCamera(31*32, 31*32);

		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

		boatTexture = new Texture("tiles/boat.png");
		boat.add(new RenderableComponent(boatTexture));
		boat.add(new PositionComponent(16f, 16f));
		boat.add(new VelocityComponent(32f, 32f, 32f));

		List<Tile> waypoints = new ArrayList<Tile>();
		waypoints.add(new WaterTile(0,0,false));
		waypoints.add(new WaterTile(5,5, false));

		RoutePlanner inputProcessor = new RoutePlanner(map, waypoints);
		Gdx.input.setInputProcessor(inputProcessor);

		boat.add(new RouteComponent(waypoints));
		engine.addEntity(boat);

		tower.add(new RenderableComponent(TextureRegistry.getTexture("projectile"), 64, 64));
		tower.add(new PositionComponent(496f, 496f));
		tower.add(new ProjectileLauncherComponent());
		engine.addEntity(tower);

		engine.addSystem(ms);
		engine.addSystem(bvs);
		engine.addSystem(pls);

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

			Affine2 rotation = new Affine2().translate(pc.x, pc.y).rotateRad(-rc.rotation).translate(-rc.width / 2, -rc.height / 2);
			batch.draw(new TextureRegion(rc.texture), rc.width, rc.height, rotation);
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
