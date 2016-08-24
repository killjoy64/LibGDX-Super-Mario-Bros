package com.stacktracemusic.mariobros.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.stacktracemusic.mariobros.entities.Mario;
import com.stacktracemusic.mariobros.util.Config;
import com.stacktracemusic.mariobros.util.Resources;

/**
 * Created by kylef_000 on 4/20/2016.
 */
public abstract class Item extends Sprite {

    protected World world;
    protected Body itemBody;
    protected Fixture itemFixture;
    protected Vector2 velocity;

    private boolean destroyed;
    private boolean readyToDestroy;

    private Texture itemSheet;

    public Item(World world, float x, float y) {
        this.world = world;
        this.destroyed = false;
        this.itemSheet = new Texture(Gdx.files.internal(Resources.ITEM_SPRITE_SHEET));

        setPosition(x, y);
        setBounds(getX(), getY(), 16 / Config.SCALE, 16 / Config.SCALE);
    }

    public void draw(Batch batch) {
        if (!destroyed) {
            super.draw(batch);
        }
    }

    public void destroy() {
        if (itemBody != null) {
            readyToDestroy = true;
        }
    }

    public void reverseVelocity() {
        velocity.x = -velocity.x;
    }

    public Texture getItemSheet() {
        return itemSheet;
    }
    public boolean isDestroyed() {
        return destroyed;
    }

    public abstract void createItem();
    public abstract void useItemOnPlayer(Mario mario);

    public void update(float delta) {
        if (readyToDestroy && !destroyed) {
            world.destroyBody(itemBody);
            destroyed = true;
        }
    }

}
