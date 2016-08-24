package com.stacktracemusic.mariobros.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Kyle Flynn on 4/8/2016.
 */
public abstract class Entity extends Sprite {

    private World world;
    private TiledMap map;
    private Texture spriteSheet;

    private boolean isDead;

    protected Body entityBody;

    private int direction;

    public Entity(World world, TiledMap map) {
        this.world = world;
        this.map = map;
        this.isDead = false;
        this.direction = 2;
    }

    public abstract void createBody();
    public abstract void update(float delta);
    public abstract TextureRegion determineFrame(float delta);

    public void setSpriteSheet(String internalPath) {
        this.spriteSheet = new Texture(Gdx.files.internal(internalPath));
    }

    public Texture getSpriteSheet() {
        return spriteSheet;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    public Body getEntityBody() {
        return entityBody;
    }
    public int getDirection() {
        return direction;
    }
    public World getWorld() {
        return world;
    }
    public TiledMap getMap() {
        return map;
    }
    public boolean isDead() {
        return isDead;
    }
    public void die() {
        isDead = true;
    }

    public Array<TextureRegion> loadAnimation(int start, int end, int y, int width, int height) {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = start; i < end; i++) {
            frames.add(new TextureRegion(getSpriteSheet(), i * width, y, width, height));
        }

        return frames;
    }

    public void dispose() {
        world.destroyBody(entityBody);
    }

}
