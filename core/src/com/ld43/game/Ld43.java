package com.ld43.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.ld43.game.entity.component.*;
import com.ld43.game.entity.system.*;
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
	private BoatVelocitySystem bvs;
	private ProjectileLauncherSystem pls = new ProjectileLauncherSystem();
	private ProjectileCollisionSystem pcs = new ProjectileCollisionSystem();
	private HealthUpdateSystem hus = new HealthUpdateSystem();
	private Family renderable = Family.all(RenderableComponent.class, PositionComponent.class).get();
	private Family hasHealthBar = Family.all(HealthComponent.class, RenderableComponent.class, PositionComponent.class).get();

	private Entity boat = new Entity();

    private Entity tower = new Entity();

    private OrthographicCamera camera;

    private ShapeRenderer shapeRenderer;
	
	@Override
	public void create () {
        TextureRegistry.loadTextures();
		map =  TileMap.fromFile("tiles/WaterTileMap.json");

		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		camera = new OrthographicCamera(31*32, 31*32);

		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

		boat.add(new RenderableComponent(TextureRegistry.getTexture("boat")));
		boat.add(new CollisionComponent(12f));
		boat.add(new PositionComponent(16f, 16f));
		boat.add(new VelocityComponent(32f, 32f, 32f));

		List<Tile> waypoints = new ArrayList<Tile>();
		waypoints.add(new WaterTile(0,0,false));
		waypoints.add(new WaterTile(5,5, false));

		RoutePlanner inputProcessor = new RoutePlanner();
		Gdx.input.setInputProcessor(inputProcessor);

		boat.add(new RouteComponent(waypoints));
		boat.add(new HealthComponent(100, 100));
		engine.addEntity(boat);

		tower.add(new RenderableComponent(TextureRegistry.getTexture("projectile"), 64, 64));
		tower.add(new PositionComponent(496f, 496f));
		tower.add(new ProjectileLauncherComponent());
		tower.add(new HealthComponent(1000, 1000));
		engine.addEntity(tower);

		engine.addSystem(ms);
		engine.addSystem(new BoatVelocitySystem(map));
		engine.addSystem(pls);
		engine.addSystem(hus);
		engine.addSystem(pcs);



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

		ImmutableArray<Entity> hasHealthBarEntities = engine.getEntitiesFor(hasHealthBar);

		for (Entity entity: hasHealthBarEntities) {

			RenderableComponent rc = entity.getComponent(RenderableComponent.class);
			PositionComponent pc = entity.getComponent(PositionComponent.class);
			HealthComponent hc = entity.getComponent(HealthComponent.class);

			float healthBarWidth = rc.width;
			float healthBarHeight = Math.max(5, rc.height / 8);
			float healthWidth = healthBarWidth * hc.getPercentageHealth();
			float healthBarPosX = pc.x - rc.width / 2;
			float healthBarPosY = pc.y + rc.height / 2 + 3;

			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rect(healthBarPosX, healthBarPosY, healthBarWidth, healthBarHeight);
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.rect(healthBarPosX, healthBarPosY, healthWidth, healthBarHeight);
			shapeRenderer.end();

		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
