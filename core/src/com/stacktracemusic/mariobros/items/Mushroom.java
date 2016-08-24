package com.stacktracemusic.mariobros.items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.stacktracemusic.mariobros.entities.Mario;
import com.stacktracemusic.mariobros.util.Config;
import com.stacktracemusic.mariobros.util.Resources;

/**
 * Created by kylef_000 on 4/20/2016.
 */
public class Mushroom extends Item {

    public Mushroom(World world, float x, float y) {
        super(world, x, y);
        setRegion(new TextureRegion(getItemSheet(), 0, 0, 16, 16));
        velocity = new Vector2(0.7f, 0);
    }

    @Override
    public void createItem() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX(), getY());

        itemBody = world.createBody(bodyDef);

        FixtureDef fixDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / Config.SCALE);
        fixDef.filter.categoryBits = Config.ITEM_BIT;
        fixDef.filter.maskBits = Config.PLAYER_BODY_BIT | Config.SOLID_TILE_BIT;
        fixDef.shape = shape;

        itemFixture = itemBody.createFixture(fixDef);
        itemFixture.setUserData(this);
        shape.dispose();

        EdgeShape sideShape = new EdgeShape();
        sideShape.set(new Vector2(-7 / Config.SCALE, 6 / Config.SCALE), new Vector2(7 / Config.SCALE, 6 / Config.SCALE));
        fixDef.shape = shape;
        fixDef.filter.categoryBits = Config.ITEM_SIDE_BIT;
        fixDef.isSensor = true;
        itemFixture = itemBody.createFixture(fixDef);
        itemFixture.setUserData(this);
        shape.dispose();
    }

    @Override
    public void useItemOnPlayer(Mario mario) {
        if (!mario.isSuperMario()) {
            mario.getAssetManager().get(Resources.MUSHROOM_USE, Sound.class).play();
            mario.setSuperMario(true);
        } else {
            mario.getAssetManager().get(Resources.MUSHROOM_USE, Sound.class).play();
        }
        destroy();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        setPosition(itemBody.getPosition().x - getWidth() / 2, itemBody.getPosition().y - getHeight() / 2);
        velocity.y = itemBody.getLinearVelocity().y;
        itemBody.setLinearVelocity(velocity);
    }
}
