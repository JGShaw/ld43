package com.ld43.game.state;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.ld43.game.entity.Boat;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.RenderableComponent;
import com.ld43.game.entity.system.BoatVelocitySystem;
import com.ld43.game.entity.system.GameConditionSystem;
import com.ld43.game.entity.tower.Tower;

import java.util.ArrayList;
import java.util.List;

import static com.ld43.game.entity.component.TowerTargetDeciderComponent.TowerBehaviour.CLOSEST;
import static com.ld43.game.entity.component.TowerTargetDeciderComponent.TowerBehaviour.RANDOM_TARGET;
import static com.ld43.game.entity.system.GameConditionSystem.Condition.TOWER_OR_BOATS;

public class LevelTwo extends LevelState {

    public static final int TOWER_HEALTH = 500;

    public LevelTwo(StateManager stateManager, Engine engine) {
        this(stateManager, engine, Tower.createTower(496, 496, TOWER_HEALTH, CLOSEST));
    }

    public LevelTwo(StateManager stateManager, Engine engine, Entity tower) {
        super(stateManager,"tiles/tileMap.json", engine, tower);
    }

    @Override
    public void create() {
        super.create();
        engine.addSystem(new GameConditionSystem(stateManager, TOWER_OR_BOATS, StateType.LOSE, StateType.PROGRESS,  StateType.LEVEL_THREE));
        engine.addSystem(new BoatVelocitySystem(map));
    }
}
