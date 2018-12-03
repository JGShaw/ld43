package com.ld43.game.state;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ld43.game.entity.Boat;
import com.ld43.game.entity.system.BoatVelocitySystem;
import com.ld43.game.entity.system.GameConditionSystem;
import com.ld43.game.entity.tower.Tower;

import static com.ld43.game.entity.component.TowerTargetDeciderComponent.TowerBehaviour.RANDOM_TARGET;
import static com.ld43.game.entity.system.GameConditionSystem.Condition.TOWER_OR_BOATS;

public class LevelOne extends LevelState {

    public static final int TOWER_HEALTH = 100;
    private static final int NUMBER_OF_SHIPS = 8;

    public LevelOne(StateManager stateManager) {
        this(stateManager, null);
    }

    public LevelOne(StateManager stateManager, Engine engine) {
        this(stateManager, engine, Tower.createTower(496, 496, TOWER_HEALTH, RANDOM_TARGET));
    }

    public LevelOne(StateManager stateManager, Engine engine, Entity tower) {
        super(stateManager,"tiles/tileMap.json", engine, tower);
    }

    private void addStartingShips() {
        for (int i = 0; i < NUMBER_OF_SHIPS; i++) {
            engine.addEntity(Boat.placeBoat( 16, 16, 0));
        }
    }

    @Override
    public void create() {
        addStartingShips();
        super.create();

        engine.addSystem(new GameConditionSystem(stateManager, TOWER_OR_BOATS, StateType.LOSE, StateType.PROGRESS, StateType.LEVEL_TWO));
        engine.addSystem(new BoatVelocitySystem(map));
    }
}
