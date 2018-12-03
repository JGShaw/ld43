package com.ld43.game.state;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ld43.game.entity.system.BoatVelocitySystem;
import com.ld43.game.entity.system.GameConditionSystem;
import com.ld43.game.entity.tower.Tower;

import static com.ld43.game.entity.component.TowerTargetDeciderComponent.TowerBehaviour.CLOSEST;
import static com.ld43.game.entity.system.GameConditionSystem.Condition.TOWER_OR_BOATS;

public class LevelThree extends LevelState {

    public static final int TOWER_HEALTH = 1000;

    public LevelThree(StateManager stateManager, Engine engine) {
        this(stateManager, engine, Tower.createTower(496, 496, TOWER_HEALTH, CLOSEST));
    }

    public LevelThree(StateManager stateManager, Engine engine, Entity tower) {
        super(stateManager,"tiles/tileMap.json", engine, tower);
    }

    @Override
    public void create() {
        super.create();
        engine.addSystem(new GameConditionSystem(stateManager, TOWER_OR_BOATS, StateType.LOSE, StateType.WIN, StateType.WIN));
        engine.addSystem(new BoatVelocitySystem(map));
    }
}
