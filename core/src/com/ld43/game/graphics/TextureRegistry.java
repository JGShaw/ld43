package com.ld43.game.graphics;

import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;

public class TextureRegistry {

    private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
    private static Texture defaultTexture;

    public static void registerTexture(String id, String fileName){

        textures.put(id, new Texture(fileName));

    }

    public static Texture getTexture(String id){

        Texture t = textures.get(id);

        return t != null ? t : defaultTexture;
    }

    public static void loadTextures(){

        registerTexture("projectile--small", "entities/projectiles/small.png");
        registerTexture("projectile--medium", "entities/projectiles/medium.png");
        registerTexture("projectile--large", "entities/projectiles/large.png");
        registerTexture("projectile--huge", "entities/projectiles/huge.png");

        registerTexture("tile-water--light", "tiles/water.png");
        registerTexture("tile-water--dark", "tiles/darkWater.png");

        defaultTexture = new Texture("badlogic.jpg");

    }

}
