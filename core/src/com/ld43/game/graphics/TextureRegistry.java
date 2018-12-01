package com.ld43.game.graphics;

import com.badlogic.gdx.graphics.Texture;

import javax.xml.soap.Text;
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

        registerTexture("projectile", "entities/projectiles/0.png");
        defaultTexture = new Texture("badlogic.jpg");

    }

}
