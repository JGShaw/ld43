package com.ld43.game.state;

import com.ld43.game.entity.Boat;
import com.ld43.game.entity.tower.Tower;

import static com.ld43.game.entity.component.TowerTargetDeciderComponent.TowerBehaviour.RANDOM_TARGET;

public class LevelOne extends LevelState {

    public LevelOne() {
        super("tiles/tileMap.json");
    }

    public void create(){
        super.create();
        engine.addEntity(Boat.placeBoat(16, 16));
        engine.addEntity(Boat.placeBoat(700, 700));
        engine.addEntity(Tower.createTower(496, 496, 1000, RANDOM_TARGET));
    }

}
