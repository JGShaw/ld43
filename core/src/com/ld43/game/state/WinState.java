package com.ld43.game.state;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.ld43.game.entity.component.HealthComponent;

public class WinState extends State {

    public int score;
    BitmapFont font = new BitmapFont();

    public WinState(StateManager stateManager, Engine engine) {
        super(stateManager, engine);
    }

    @Override
    public void create() {
        super.create();

        font.getData().scale(4);
        for(Entity boat: engine.getEntitiesFor(isBoat)){
            HealthComponent hc = boat.getComponent(HealthComponent.class);
            score += hc.healthCurrent;
        }

    }

    @Override
    void render() {
        Gdx.gl.glClearColor(0, 0.6f,  0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, "You Win! Score: XXXX");


        batch.begin();
        font.draw(batch, "You Win! Score: " + score, 496 - layout.width / 2 - 100, 496 - layout.height);
        batch.end();

    }

    @Override
    void destroy() {

    }

}
