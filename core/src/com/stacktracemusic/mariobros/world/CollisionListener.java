package com.stacktracemusic.mariobros.world;

import com.badlogic.gdx.physics.box2d.*;
import com.stacktracemusic.mariobros.entities.Mario;
import com.stacktracemusic.mariobros.items.Item;
import com.stacktracemusic.mariobros.util.Config;
import com.stacktracemusic.mariobros.world.tiles.Tile;

/**
 * Created by kylef_000 on 4/19/2016.
 */
public class CollisionListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA  = contact.getFixtureA();
        Fixture fixB =  contact.getFixtureB();

        int collision = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if (collision == (Config.PLAYER_HEAD_BIT | Config.SOLID_TILE_BIT)) {
            if (fixA.getUserData() instanceof Mario) {
                ((Tile) fixB.getUserData()).onPlayerCollide((Mario) fixA.getUserData());
            } else {
                ((Tile) fixA.getUserData()).onPlayerCollide((Mario) fixB.getUserData());
            }
        }
        if (collision == (Config.PLAYER_BODY_BIT | Config.ITEM_BIT)) {
            if (fixA.getUserData() instanceof Mario) {
                ((Item) fixB.getUserData()).useItemOnPlayer((Mario) fixA.getUserData());
            } else {
                ((Item) fixA.getUserData()).useItemOnPlayer((Mario) fixB.getUserData());
            }
        }
        if (collision == (Config.ITEM_SIDE_BIT | Config.SOLID_TILE_BIT)) {
            if (fixA.getUserData() instanceof Item) {
                ((Item) fixA.getUserData()).reverseVelocity();
            } else {
                ((Item) fixB.getUserData()).reverseVelocity();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
