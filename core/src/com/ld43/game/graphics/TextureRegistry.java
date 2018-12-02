package com.ld43.game.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class TextureRegistry {

    private static HashMap<String, TextureRegion> textures = new HashMap<String, TextureRegion>();
    private static TextureRegion defaultTexture;

    public static void registerTexture(String id, String fileName){

        textures.put(id, new TextureRegion(new Texture(fileName)));

    }

    public static void registerTexture(String id, String fileName, int x, int y, int width, int height){

        textures.put(id, new TextureRegion(new Texture(fileName), x, y, width, height));

    }

    public static TextureRegion getTexture(String id){

        TextureRegion t = textures.get(id);

        if(t == null) {
            System.out.println("No texture found for: " + id);
        }

        return t != null ? t : defaultTexture;
    }

    public static void loadTextures(){

        registerTexture("projectile--small", "entities/projectiles/small.png");
        registerTexture("projectile--medium", "entities/projectiles/medium.png");
        registerTexture("projectile--large", "entities/projectiles/large.png");
        registerTexture("projectile--huge", "entities/projectiles/huge.png");

        registerTexture("tile-water--0", "tiles/tileMap.png", 0, 0, 32, 32);
        registerTexture("tile-water--1", "tiles/tileMap.png", 32, 0, 32, 32);
        registerTexture("tile-water--2", "tiles/tileMap.png", 64, 0, 32, 32);
        registerTexture("tile-water--3", "tiles/tileMap.png", 96, 0, 32, 32);
        registerTexture("tile-water--4", "tiles/tileMap.png", 128, 0, 32, 32);
        registerTexture("tile-water--5", "tiles/tileMap.png", 160, 0, 32, 32);
        registerTexture("tile-water--6", "tiles/tileMap.png", 0, 32, 32, 32);
        registerTexture("tile-water--7", "tiles/tileMap.png", 32, 32, 32, 32);
        registerTexture("tile-water--8", "tiles/tileMap.png", 64, 32, 32, 32);
        registerTexture("tile-water--9", "tiles/tileMap.png", 96, 32, 32, 32);
        registerTexture("tile-water--10", "tiles/tileMap.png", 128, 32, 32, 32);
        registerTexture("tile-water--11", "tiles/tileMap.png", 160, 32, 32, 32);
        registerTexture("tile-water--12", "tiles/tileMap.png", 0, 64, 32, 32);
        registerTexture("tile-water--13", "tiles/tileMap.png", 32, 64, 32, 32);

        registerTexture("tile-land--0", "tiles/tileMap.png", 0, 96, 32, 32);
        registerTexture("tile-land--1", "tiles/tileMap.png", 32, 96, 32, 32);
        registerTexture("tile-land--2", "tiles/tileMap.png", 64, 96, 32, 32);
        registerTexture("tile-land--3", "tiles/tileMap.png", 96,  96, 32, 32);
        registerTexture("tile-land--4", "tiles/tileMap.png", 128, 96, 32, 32);
        registerTexture("tile-land--5", "tiles/tileMap.png", 160, 96, 32, 32);
        registerTexture("tile-land--6", "tiles/tileMap.png", 0,  128, 32, 32);
        registerTexture("tile-land--7", "tiles/tileMap.png", 32, 128, 32, 32);
        registerTexture("tile-land--8", "tiles/tileMap.png", 64, 128, 32, 32);

        registerTexture("tile-land", "tiles/land.png");

        registerTexture("boat-0", "entities/boat/boat-0.png");
        registerTexture("boat-0--sunk", "entities/boat/boat-0--sunk.png");
        registerTexture("boat-1", "entities/boat/boat-1.png");
        registerTexture("boat-1--sunk", "entities/boat/boat-1--sunk.png");

        registerTexture("tower", "entities/tower/tower.png");
        registerTexture("gun", "entities/tower/gun.png");

        defaultTexture = new TextureRegion(new Texture("badlogic.jpg"));

    }

}
