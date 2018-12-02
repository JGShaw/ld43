package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {

    public float healthMax;
    public float healthCurrent;
    public float healthBetween;
    public boolean showHealth = true;

    public HealthComponent(float healthMax, float healthCurrent) {
        this.healthMax = healthMax;
        this.healthCurrent = healthCurrent;
        this.healthBetween = healthCurrent;
    }

    public void update(float deltaTime){

        if(healthBetween > healthCurrent){

            healthBetween -= Math.max((healthBetween - healthCurrent) * deltaTime, 1);

            if(healthBetween <= healthCurrent){
                healthBetween = healthCurrent;
            }
        }

        System.out.println("Health: " + healthCurrent + "  , " + healthBetween);

    }

    public void takeDamage(float damage){
        healthCurrent = Math.max(healthCurrent - damage, 0);
    }

    public boolean isDead(){
        return healthCurrent <= 0;
    }

    public float getPercentageHealth() {

        return healthBetween / healthMax;

    }
}
