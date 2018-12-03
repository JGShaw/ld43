package com.ld43.game.state;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.ld43.game.entity.component.FocusableComponent;
import com.ld43.game.entity.component.HealthComponent;
import com.ld43.game.entity.component.RenderableComponent;
import com.ld43.game.entity.system.GameConditionSystem;
import com.ld43.game.entity.system.HealthUpdateSystem;
import com.ld43.game.input.InputHandler;
import sun.java2d.InvalidPipeException;

public class ProgressState extends State {

    public ImmutableArray<Entity> boats;
    public int selectedBoat = 0;
    public long selectedChangedAt;

    private StateType next;

    public ProgressState(StateManager stateManager, Engine engine, StateType next){
        super(stateManager, engine);
        this.next = next;
    }

    @Override
    public void create() {
        super.create();

        engine.addSystem(new HealthUpdateSystem(false));
        engine.addSystem(new GameConditionSystem(stateManager, GameConditionSystem.Condition.NONE, StateType.LEVEL_ONE, next, StateType.WIN));

        InputHandler.nextState = false;
    }

    @Override
    void render() {
        boats = engine.getEntitiesFor(isBoat);
        engine.update(Gdx.graphics.getDeltaTime());

        if(InputHandler.isKeyDown[22]){
            if(System.currentTimeMillis() - selectedChangedAt > 200){
                selectedBoat = (selectedBoat + 1) % boats.size();
                selectedChangedAt = System.currentTimeMillis();
            }
        }
        if(InputHandler.isKeyDown[21]){
            if(System.currentTimeMillis() - selectedChangedAt > 200){
                selectedBoat = (selectedBoat - 1 + boats.size()) % boats.size();
                selectedChangedAt = System.currentTimeMillis();
            }
        }
        if(InputHandler.isKeyDown[62]){
            Entity toSacrifice = boats.get(selectedBoat);
            HealthComponent hc = toSacrifice.getComponent(HealthComponent.class);
            if(hc.healthCurrent != 0){
                hc.healthCurrent = 0;

                for(int i = 0; i < boats.size(); i++){
                    Entity boat = boats.get(i);
                    HealthComponent bhc = boat.getComponent(HealthComponent.class);
                    if(bhc.healthCurrent != 0){
                        float newHealth = Math.min(bhc.healthCurrent + 50, bhc.healthMax);
                        bhc.healthCurrent = newHealth;
                    }
                }
            }
        }
        if(InputHandler.isKeyDown[53]){
            InputHandler.nextState = true;
        }

        setFocusedBoat();

        Gdx.gl.glClearColor(0, 0,  1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderBoats();

    }

    private void setFocusedBoat() {

        for(int i = 0; i < boats.size(); i++){
            FocusableComponent fc = boats.get(i).getComponent(FocusableComponent.class);
            fc.focused = i == selectedBoat;
        }

    }

    @Override
    void destroy() {

    }

    private void renderBoats() {

        int index = 0;
        float startPadding = 100;
        float padding = 600 / (boats.size() - 1);

        for (Entity entity : boats) {
            FocusableComponent fc = entity.getComponent(FocusableComponent.class);

            float x = startPadding + index * padding;
            float y = 496;

            if(fc.focused){
                shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                Color c = Color.ORANGE;
                c.a = 0.7f;
                shapeRenderer.setColor(c);
                shapeRenderer.circle(x,y, 48);
                shapeRenderer.end();
            }
            index++;
        }
        index = 0;
        batch.begin();
        for (Entity entity : boats) {
            RenderableComponent rc = entity.getComponent(RenderableComponent.class);
            HealthComponent hc = entity.getComponent(HealthComponent.class);

            float x = startPadding + index * padding;
            float y = 496;

            Affine2 rotation = new Affine2().translate(x, y).scale(3f, 3f).translate(-rc.width / 2, -rc.height / 2);
            batch.draw(rc.texture, rc.width, rc.height, rotation);
            index++;
        }
        batch.end();
        index = 0;
        for (Entity entity : boats) {
            RenderableComponent rc = entity.getComponent(RenderableComponent.class);
            HealthComponent hc = entity.getComponent(HealthComponent.class);

            float x = startPadding + index * padding;
            float y = 496;

            float healthBarWidth = rc.width * 0.7f * 3;
            float healthBarHeight = Math.max(5, (rc.height * 3) / 8);
            float healthWidth = healthBarWidth * hc.getPercentageHealth();
            float healthBarPosX = x - (rc.width * 0.7f * 3) / 2;
            float healthBarPosY = y + (rc.height * 3) / 2 + 3;

            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(healthBarPosX, healthBarPosY, healthBarWidth, healthBarHeight);
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(healthBarPosX, healthBarPosY, healthWidth, healthBarHeight);
            shapeRenderer.end();
            index++;
        }

    }



}
