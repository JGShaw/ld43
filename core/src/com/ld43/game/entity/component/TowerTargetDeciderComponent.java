package com.ld43.game.entity.component;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class TowerTargetDeciderComponent implements Component {

    public static final float MAX_SIGHT_RANGE = 420;

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

    public Entity getTarget(float towerX, float towerY, Engine engine) {

        ImmutableArray<Entity> boats = engine.getEntitiesFor(familyBoat);

        List<Entity> inRange = new ArrayList<Entity>();
        for(Entity boat: boats){
            PositionComponent pos = pm.get(boat);
            if(Math.sqrt(Math.pow(pos.x - towerX, 2) + Math.pow(pos.y - towerY, 2)) < MAX_SIGHT_RANGE){
                inRange.add(boat);
            }
        }

        switch (behaviour){
            case CLOSEST:
                return getClosest(towerX, towerY, inRange);
            case LOWEST_MAGNITUDE_HEALTH:
                return getLowestMagnitudeHealth(towerX, towerY, inRange);
            case HIGHEST_MAGNITUDE_HEALTH:
                return getHighestMagnitudeHealth(towerX, towerY, inRange);
            case LOWEST_PERCENTAGE_HEALTH:
                return getLowestPercentageHealth(towerX, towerY, inRange);
            case HIGHEST_PERCENTAGE_HEALTH:
                return getHighestPercentageHealth(towerX, towerY, inRange);
            case RANDOM_TARGET:
                return randomTarget(towerX, towerY, inRange);
            case RANDOM:
            default:
                return null;
        }

    }

    private Entity randomTarget(float towerX, float towerY, List<Entity> boats) {

        if(boats.size() == 0){
            return null;
        } else {
            return boats.get(MathUtils.random(0, boats.size()-1));
        }

    }

    private Entity getClosest(float towerX, float towerY, List<Entity> boats){

        if(boats.size() == 0) return null;

        Entity closest = null;
        float closestDistance = Float.MAX_VALUE;

        for(Entity boat: boats){

            PositionComponent pos = pm.get(boat);

            float distanceBetween = (float)Math.sqrt(Math.pow(pos.x - towerX, 2) + Math.pow(pos.y - towerY, 2));
            if(distanceBetween < closestDistance){
                closest = boat;
                closestDistance = distanceBetween;
            }
        }
        return closest;
    }

    private Entity getLowestMagnitudeHealth(float towerX, float towerY, List<Entity> boats) {

        if(boats.size() == 0) return null;

        Entity target = null;
        float lowestHealth = Float.MAX_VALUE;

        for(Entity boat: boats){
            HealthComponent health = hm.get(boat);

            if(health.healthCurrent < lowestHealth){
                target = boat;
                lowestHealth = health.healthCurrent;
            }
        }
        return target;
    }

    private Entity getLowestPercentageHealth(float towerX, float towerY, List<Entity> boats) {

        if(boats.size() == 0) return null;

        Entity target = null;
        float lowestHealth = Float.MAX_VALUE;

        for(Entity boat: boats){

            HealthComponent health = hm.get(boat);

            float percentageHealth = health.healthCurrent / health.healthMax;

            if(percentageHealth < lowestHealth){
                target = boat;
                lowestHealth = percentageHealth;
            }
        }
        return target;
    }

    private Entity getHighestMagnitudeHealth(float towerX, float towerY, List<Entity> boats) {

        if(boats.size() == 0) return null;

        Entity target = null;
        float highestHealth = 0;

        for(Entity boat: boats){

            HealthComponent health = hm.get(boat);

            if(health.healthCurrent > highestHealth){
                target = boat;
                highestHealth = health.healthCurrent;
            }
        }
        return target;
    }

    private Entity getHighestPercentageHealth(float towerX, float towerY, List<Entity> boats) {

        if(boats.size() == 0) return null;

        Entity target = null;
        float highestHealth = 0;

        for(Entity boat: boats){

            HealthComponent health = hm.get(boat);

            float percentageHealth = health.healthCurrent / health.healthMax;

            if(percentageHealth > highestHealth){
                target = boat;
                highestHealth = percentageHealth;
            }
        }
        return target;

    }

}
