package com.ld43.game.state;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.ld43.game.entity.Boat;
import com.ld43.game.entity.tower.Tower;

import java.util.ArrayList;
import java.util.List;

import static com.ld43.game.entity.component.TowerTargetDeciderComponent.TowerBehaviour.RANDOM_TARGET;

public class LevelOne extends LevelState {

    private static final int NUMBER_OF_SHIPS = 8;

    public LevelOne(StateManager stateManager) {
        this(stateManager, getStartingShips());
    }

    public LevelOne(StateManager stateManager, List<Entity> availableShips) {
        this(stateManager, availableShips, Tower.createTower(496, 496, 1000, RANDOM_TARGET));
    }

    public LevelOne(StateManager stateManager, List<Entity> availableShips, Entity tower) {
        super(stateManager,"tiles/tileMap.json", availableShips, tower);
    }

    private static List<Entity> getStartingShips() {

        float angleBetween = MathUtils.PI2 / NUMBER_OF_SHIPS;

        List<Entity> availableShips = new ArrayList<Entity>();
        for (int i = 0; i < NUMBER_OF_SHIPS; i++) {
            float angle = i * angleBetween;

            float x = 496 + (float)Math.sin(angle) * 460;
            float y = 496 + (float)Math.cos(angle) * 460;

            availableShips.add(Boat.placeBoat( x, y, angle + MathUtils.PI));
        }
        return availableShips;
    }

}
