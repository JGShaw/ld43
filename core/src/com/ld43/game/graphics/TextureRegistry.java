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

        registerTexture("tile-water--0", "tiles/waterTileMap.png", 0, 0, 16, 16);
        registerTexture("tile-water--1", "tiles/waterTileMap.png", 16, 0, 16, 16);
        registerTexture("tile-water--2", "tiles/waterTileMap.png", 32, 0, 16, 16);
        registerTexture("tile-water--3", "tiles/waterTileMap.png", 48, 0, 16, 16);
        registerTexture("tile-water--4", "tiles/waterTileMap.png", 0, 16, 16, 16);
        registerTexture("tile-water--5", "tiles/waterTileMap.png", 16, 16, 16, 16);
        registerTexture("tile-water--6", "tiles/waterTileMap.png", 32, 16, 16, 16);
        registerTexture("tile-water--7", "tiles/waterTileMap.png", 48, 16, 16, 16);
        registerTexture("tile-water--8", "tiles/waterTileMap.png", 0, 32, 16, 16);
        registerTexture("tile-water--9", "tiles/waterTileMap.png", 16, 32, 16, 16);
        registerTexture("tile-water--10", "tiles/waterTileMap.png", 32, 32, 16, 16);
        registerTexture("tile-water--11", "tiles/waterTileMap.png", 48, 32, 16, 16);
        registerTexture("tile-water--12", "tiles/waterTileMap.png", 0, 48, 16, 16);
        registerTexture("tile-water--13", "tiles/waterTileMap.png", 16, 48, 16, 16);

        registerTexture("tile-land", "tiles/land.png");

        registerTexture("boat", "tiles/boat.png");

        defaultTexture = new TextureRegion(new Texture("badlogic.jpg"));

    }

}
