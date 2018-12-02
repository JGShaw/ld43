package com.ld43.game.state;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.ld43.game.entity.component.*;
import com.ld43.game.entity.system.*;
import com.ld43.game.input.InputHandler;
import com.ld43.game.map.TileMap;

import java.util.List;

public abstract class LevelState extends State {

    public static final int NUM_OF_TILES = 31;

    public List<Entity> availableBoats;
    public Entity tower;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private ShapeRenderer routeRenderer;
    private SpriteBatch batch;
    private TileMap map;

    // Ashley ECS
    Engine engine = new Engine();

    Family renderable = Family.all(RenderableComponent.class, PositionComponent.class).exclude(UnderwaterComponent.class).get();
    Family renderableUnderwater = Family.all(RenderableComponent.class, PositionComponent.class, UnderwaterComponent.class).get();
    Family hasHealthBar = Family.all(HealthComponent.class, RenderableComponent.class, PositionComponent.class).get();
    Family canBeFocused = Family.all(FocusableComponent.class, RenderableComponent.class, PositionComponent.class).get();

    public LevelState(StateManager stateManager, String tileMapFileName, List<Entity> availableBoats, Entity tower){
        super(stateManager);
        map = TileMap.fromFile(tileMapFileName);
        this.availableBoats = availableBoats;
        this.tower = tower;

    }

    @Override
    public void create() {

        batch = new SpriteBatch(2000);
        shapeRenderer = new ShapeRenderer();
        routeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera(31 * 32, 31 * 32);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        InputHandler inputProcessor = new InputHandler();
        Gdx.input.setInputProcessor(inputProcessor);

        engine.addSystem(new MovementSystem());
        engine.addSystem(new BoatVelocitySystem(map));
        engine.addSystem(new ProjectileLauncherSystem());
        engine.addSystem(new HealthUpdateSystem());
        engine.addSystem(new ProjectileCollisionSystem());
        engine.addSystem(new BoatProjectileLauncherSystem());
        engine.addSystem(new FocusedSystem());
        engine.addSystem(new GameConditionSystem(stateManager, new LoseState(stateManager), new WinState(stateManager)));

        engine.addEntity(tower);
        for (Entity boat: availableBoats) {
            engine.addEntity(boat);
        }

    }

    @Override
    public void render() {

        engine.update(Gdx.graphics.getDeltaTime());
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        renderEntities(renderableUnderwater);
        batch.end();

        renderRoutes();

        batch.begin();
        map.render(batch);
        batch.end();

        renderFocused();

        batch.begin();
        renderEntities(renderable);
        batch.end();

        renderHealthBars();

    }

    @Override
    public void destroy() {
        batch.dispose();
    }

    private void renderHealthBars() {
        ImmutableArray<Entity> hasHealthBarEntities = engine.getEntitiesFor(hasHealthBar);
        for (Entity entity : hasHealthBarEntities) {
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

    }

    private void renderFocused() {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(canBeFocused);
        for (Entity entity : entities) {
            FocusableComponent fc = entity.getComponent(FocusableComponent.class);

            if (fc.focused) {
                RenderableComponent rc = entity.getComponent(RenderableComponent.class);
                PositionComponent pc = entity.getComponent(PositionComponent.class);

                shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(Color.ORANGE);
                shapeRenderer.circle(pc.x, pc.y, rc.width * 0.6f);
                shapeRenderer.end();
            }
        }
    }

    private void renderRoutes() {
        ImmutableArray<Entity> routes = engine.getEntitiesFor(Family.all(RouteComponent.class, FocusableComponent.class).get());
        for (Entity entity : routes) {
            RouteComponent rc = entity.getComponent(RouteComponent.class);
            FocusableComponent fc = entity.getComponent(FocusableComponent.class);

            if (!fc.focused) {
                continue;
            }

            routeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            rc.renderRoute(routeRenderer);
        }
    }

    private void renderEntities(Family family) {
        ImmutableArray<Entity> renderablesUnderwater = engine.getEntitiesFor(family);

        for (Entity entity : renderablesUnderwater) {
            RenderableComponent rc = entity.getComponent(RenderableComponent.class);
            PositionComponent pc = entity.getComponent(PositionComponent.class);

            Affine2 rotation = new Affine2().translate(pc.x, pc.y).rotateRad(-rc.rotation).translate(-rc.width / 2, -rc.height / 2);
            batch.draw(rc.texture, rc.width, rc.height, rotation);
        }
    }
}
