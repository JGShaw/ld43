package com.ld43.game.entity.component;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;

public class TowerTargetDeciderComponent implements Component {

    public enum TowerBehaviour {
        CLOSEST,
        LOWEST_MAGNITUDE_HEALTH,
        HIGHEST_MAGNITUDE_HEALTH,
        LOWEST_PERCENTAGE_HEALTH,
        HIGHEST_PERCENTAGE_HEALTH,
        RANDOM_TARGET,
        RANDOM
    }

    Family familyBoat = Family.all(BoatComponent.class, PositionComponent.class, HealthComponent.class).get();
    ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

    private TowerBehaviour behaviour;

    public TowerTargetDeciderComponent(){
        this(TowerBehaviour.RANDOM_TARGET);
    }

    public TowerTargetDeciderComponent(TowerBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    public void setBehaviour(TowerBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    public float getAngle(float towerX, float towerY, Engine engine) {

        ImmutableArray<Entity> boats = engine.getEntitiesFor(familyBoat);

        switch (behaviour){
            case CLOSEST:
                return getClosest(towerX, towerY, boats);
            case LOWEST_MAGNITUDE_HEALTH:
                return getLowestMagnitudeHealth(towerX, towerY, boats);
            case HIGHEST_MAGNITUDE_HEALTH:
                return getHighestMagnitudeHealth(towerX, towerY, boats);
            case LOWEST_PERCENTAGE_HEALTH:
                return getLowestPercentageHealth(towerX, towerY, boats);
            case HIGHEST_PERCENTAGE_HEALTH:
                return getHighestPercentageHealth(towerX, towerY, boats);
            case RANDOM_TARGET:
                return randomTarget(towerX, towerY, boats);
            case RANDOM:
            default:
                return MathUtils.random(0, MathUtils.PI2);
        }

    }

    private float randomTarget(float towerX, float towerY, ImmutableArray<Entity> boats) {

        if(boats.size() == 0){
            return MathUtils.random(0, MathUtils.PI2);
        } else {
            Entity random = boats.random();
            PositionComponent pos = pm.get(random);
            return (float)com.ld43.game.math.MathUtils.angleBetweenPoints(towerX, towerY, pos.x, pos.y);
        }

    }

    private float getClosest(float towerX, float towerY, ImmutableArray<Entity> boats){

        PositionComponent closest = null;
        float closestDistance = Float.MAX_VALUE;

        for(Entity boat: boats){

            PositionComponent pos = pm.get(boat);

            float distanceBetween = (float)Math.sqrt(Math.pow(pos.x - towerX, 2) + Math.pow(pos.y - towerY, 2));
            if(distanceBetween < closestDistance){
                closest = pos;
                closestDistance = distanceBetween;
            }
        }

        if(closest == null){
            return MathUtils.random(0, MathUtils.PI2);
        } else {
            return (float)com.ld43.game.math.MathUtils.angleBetweenPoints(towerX, towerY, closest.x, closest.y);
        }

    }

    private float getLowestMagnitudeHealth(float towerX, float towerY, ImmutableArray<Entity> boats) {

        PositionComponent target = null;
        float lowestHealth = Float.MAX_VALUE;

        for(Entity boat: boats){

            HealthComponent health = hm.get(boat);

            if(health.healthCurrent < lowestHealth){
                target = pm.get(boat);
                lowestHealth = health.healthCurrent;
            }
        }

        if(target == null){
            return MathUtils.random(0, MathUtils.PI2);
        } else {
            return (float)com.ld43.game.math.MathUtils.angleBetweenPoints(towerX, towerY, target.x, target.y);
        }

    }

    private float getLowestPercentageHealth(float towerX, float towerY, ImmutableArray<Entity> boats) {

        PositionComponent target = null;
        float lowestHealth = Float.MAX_VALUE;

        for(Entity boat: boats){

            HealthComponent health = hm.get(boat);

            float percentageHealth = health.healthCurrent / health.healthMax;

            if(percentageHealth < lowestHealth){
                target = pm.get(boat);
                lowestHealth = percentageHealth;
            }
        }

        if(target == null){
            return MathUtils.random(0, MathUtils.PI2);
        } else {
            return (float)com.ld43.game.math.MathUtils.angleBetweenPoints(towerX, towerY, target.x, target.y);
        }

    }

    private float getHighestMagnitudeHealth(float towerX, float towerY, ImmutableArray<Entity> boats) {

        PositionComponent target = null;
        float highestHealth = 0;

        for(Entity boat: boats){

            HealthComponent health = hm.get(boat);

            if(health.healthCurrent > highestHealth){
                target = pm.get(boat);
                highestHealth = health.healthCurrent;
            }
        }

        if(target == null){
            return MathUtils.random(0, MathUtils.PI2);
        } else {
            return (float)com.ld43.game.math.MathUtils.angleBetweenPoints(towerX, towerY, target.x, target.y);
        }

    }

    private float getHighestPercentageHealth(float towerX, float towerY, ImmutableArray<Entity> boats) {

        PositionComponent target = null;
        float highestHealth = 0;

        for(Entity boat: boats){

            HealthComponent health = hm.get(boat);

            float percentageHealth = health.healthCurrent / health.healthMax;

            if(percentageHealth > highestHealth){
                target = pm.get(boat);
                highestHealth = percentageHealth;
            }
        }

        if(target == null){
            return MathUtils.random(0, MathUtils.PI2);
        } else {
            return (float)com.ld43.game.math.MathUtils.angleBetweenPoints(towerX, towerY, target.x, target.y);
        }

    }

}
