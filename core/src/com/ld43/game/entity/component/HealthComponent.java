package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.ld43.game.graphics.TextureRegistry;

public class HealthComponent implements Component {

    public float healthMax;
    public float healthCurrent;
    public float healthBetween;
    public boolean showHealth = true;
    public String deathTextureName;

    public HealthComponent(float healthMax, float healthCurrent) {
        this.healthMax = healthMax;
        this.healthCurrent = healthCurrent;
        this.healthBetween = healthCurrent;
    }

    public HealthComponent(float healthMax, float healthCurrent, String deathTextureName) {
        this(healthMax, healthCurrent);
        this.deathTextureName = deathTextureName;
    }

    public void update(float deltaTime){

        if(healthBetween > healthCurrent){

            healthBetween -= Math.max((healthBetween - healthCurrent) * deltaTime, 1);

            if(healthBetween <= healthCurrent){
                healthBetween = healthCurrent;
            }
        }
    }

    public void takeDamage(float damage){
        healthCurrent = Math.max(healthCurrent - damage, 0);
    }

    public boolean isDead(){
        return healthBetween <= 0;
    }

    public float getPercentageHealth() {

        return healthBetween / healthMax;

    }
}
