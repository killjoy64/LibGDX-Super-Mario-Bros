package com.stacktracemusic.mariobros.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.stacktracemusic.mariobros.util.Config;
import com.stacktracemusic.mariobros.util.Resources;
import com.stacktracemusic.mariobros.world.GameData;

import java.util.HashMap;

/**
 * Created by Kyle Flynn on 4/8/2016.
 */
public class Mario extends Entity {

    private Animation walking;
    private TextureRegion standing;
    private TextureRegion jumping;
    private TextureRegion dead;

    private Animation superWalking;
    private TextureRegion superStanding;
    private TextureRegion superJumping;
    private TextureRegion superCrouching;

    private float timer;

    private HashMap<Integer, Boolean> keys;

    public int LEFT = 0;
    public int RIGHT = 1;
    public int UP = 2;
    public int DOWN = 3;
    public int SPACE = 4;

    private boolean switchedDirections;
    private boolean superMario;
    private boolean redefineReady;

    private AssetManager assetManager;
    private GameData gameData;

    private enum State {
        RUNNING_LEFT,
        RUNNING_RIGHT,
        JUMPING,
        STANDING,
        DEAD
    }

    private State currState;
    private State prevState;

    public Mario(World world, TiledMap map) {
        super(world, map);
        setSpriteSheet(Resources.MARIO_SPRITE_SHEET);

        keys = new HashMap<Integer, Boolean>();

        keys.put(UP, false);
        keys.put(DOWN, false);
        keys.put(LEFT, false);
        keys.put(RIGHT, false);
        keys.put(SPACE, false);

        walking = new Animation(0.2f, loadAnimation(1, 4, 0, 16, 16));
        standing = new TextureRegion(getSpriteSheet(), 0, 0, 16, 16);
        jumping = new TextureRegion(getSpriteSheet(), 64, 0, 16, 16);
        dead = new TextureRegion(getSpriteSheet(), 208, 0, 16, 16);

        superWalking = new Animation(0.2f, loadAnimation(1, 4, 16, 16, 32));
        superStanding = new TextureRegion(getSpriteSheet(), 0, 16, 16, 32);
        superJumping = new TextureRegion(getSpriteSheet(), 64, 16, 16, 32);

        superMario = false;

        if (superMario) {
            setRegion(superStanding);
        } else {
            setRegion(standing);
        }

        switchedDirections = true;

        createBody();
    }

    @Override
    public void createBody() {
        Vector2 currentPosition;

        if (!superMario) {
            setBounds(0, 0, 16 / Config.SCALE, 16 / Config.SCALE);
            if (entityBody != null) {
                currentPosition = entityBody.getPosition();
                getWorld().destroyBody(entityBody);
            } else {
                currentPosition = new Vector2(32 / Config.SCALE, 128 / Config.SCALE);
            }

            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(currentPosition);
            bodyDef.type = BodyDef.BodyType.DynamicBody;

            entityBody = getWorld().createBody(bodyDef);

            FixtureDef fixDef = new FixtureDef();
            CircleShape shape = new CircleShape();
            shape.setRadius(7 / Config.SCALE);
            fixDef.shape = shape;
            fixDef.restitution = 0.0f;
            fixDef.filter.categoryBits = Config.PLAYER_BODY_BIT;
            fixDef.filter.maskBits = Config.SOLID_TILE_BIT | Config.ITEM_BIT;
            entityBody.createFixture(fixDef).setUserData(this);
            shape.dispose();

            EdgeShape headSensor = new EdgeShape();
            headSensor.set(new Vector2(-2 / Config.SCALE, 6 / Config.SCALE), new Vector2(2 / Config.SCALE, 6 / Config.SCALE));
            fixDef.shape = headSensor;
            fixDef.isSensor = true;
            fixDef.filter.categoryBits = Config.PLAYER_HEAD_BIT;
            entityBody.createFixture(fixDef).setUserData(this);

            shape.dispose();
        } else {
            setBounds(0, 0, 16 / Config.SCALE, 32 / Config.SCALE);
            if (entityBody != null) {
                currentPosition = entityBody.getPosition();
                getWorld().destroyBody(entityBody);
            } else {
                currentPosition = new Vector2(32 / Config.SCALE, 128 / Config.SCALE);
            }

            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(currentPosition.add(0, 0 / Config.SCALE));
            bodyDef.type = BodyDef.BodyType.DynamicBody;

            entityBody = getWorld().createBody(bodyDef);

            FixtureDef fixDef = new FixtureDef();
            CircleShape shape = new CircleShape();
            shape.setRadius(7 / Config.SCALE);
            fixDef.shape = shape;
            fixDef.restitution = 0.0f;
            fixDef.filter.categoryBits = Config.PLAYER_BODY_BIT;
            fixDef.filter.maskBits = Config.SOLID_TILE_BIT | Config.ITEM_BIT;
            entityBody.createFixture(fixDef).setUserData(this);
            shape.setPosition(new Vector2(0, -14 / Config.SCALE));
            entityBody.createFixture(fixDef).setUserData(this);
            shape.dispose();

            EdgeShape headSensor = new EdgeShape();
            headSensor.set(new Vector2(-2 / Config.SCALE, 6 / Config.SCALE), new Vector2(2 / Config.SCALE, 6 / Config.SCALE));
            fixDef.shape = headSensor;
            fixDef.isSensor = true;
            fixDef.filter.categoryBits = Config.PLAYER_HEAD_BIT;
            entityBody.createFixture(fixDef).setUserData(this);

            shape.dispose();
        }
        redefineReady = false;
    }

    @Override
    public void update(float delta) {

        if (currState != State.DEAD && !isDead()) {
            if (keys.get(UP)) {
                if (currState != State.JUMPING && entityBody.getLinearVelocity().y == 0) {
                    if (superMario) {
                        assetManager.get(Resources.MARIO_SUPER_JUMP_SOUND, Sound.class).play();
                    } else {
                        assetManager.get(Resources.MARIO_SMALL_JUMP_SOUND, Sound.class).play();
                    }
                    entityBody.applyLinearImpulse(new Vector2(0, 4f), entityBody.getWorldCenter(), true);
                    currState = State.JUMPING;
                }
            }
            if (keys.get(RIGHT) && entityBody.getLinearVelocity().x <= 2) {
                entityBody.applyLinearImpulse(new Vector2(0.1f, 0), entityBody.getWorldCenter(), true);
            }
            if (keys.get(LEFT) && entityBody.getLinearVelocity().x >= -2) {
                entityBody.applyLinearImpulse(new Vector2(-0.1f, 0), entityBody.getWorldCenter(), true);
            }
        } else {
            // Then this method is run only once
            if (isDead() == false) {
                assetManager.get(Resources.MARIO_DIE, Sound.class).play();
                die();
            }
        }

        if (redefineReady) {
            createBody();
        }

        if (superMario) {
            setPosition(entityBody.getPosition().x - getWidth() / 2, entityBody.getPosition().y - getHeight() / 2 - 6 / Config.SCALE);
        } else {
            setPosition(entityBody.getPosition().x - getWidth() / 2, entityBody.getPosition().y - getHeight() / 2);
        }
        setRegion(determineFrame(delta));
    }

    @Override
    public TextureRegion determineFrame(float delta) {
        TextureRegion region;

        currState = getState();

        timer += delta;

        if (currState != State.DEAD) {
            if (currState == prevState) {
                timer += delta;
            } else {
                timer = 0;
            }

            if (currState == State.RUNNING_LEFT || currState == State.RUNNING_RIGHT) {
                if (superMario) {
                    region = superWalking.getKeyFrame(timer, true);
                } else {
                    region = walking.getKeyFrame(timer, true);
                }
            } else if (currState == State.JUMPING) {
                if (superMario) {
                    region = superJumping;
                } else {
                    region = jumping;
                }
            } else {
                if (superMario) {
                    region = superStanding;
                } else {
                    region = standing;
                }
            }

            if ((currState == State.RUNNING_LEFT || !switchedDirections) && !region.isFlipX()) {
                region.flip(true, false);
                switchedDirections = false;
            } else if ((currState == State.RUNNING_RIGHT || switchedDirections) && region.isFlipX()) {
                region.flip(true, false);
                switchedDirections = true;
            }
        } else {
            region = dead;
        }
        prevState = currState;

        return region;
    }

    public State getState() {
        if (entityBody.getPosition().x < -.16 || entityBody.getPosition().y < -.16) {
            return State.DEAD;
        } else if (Math.abs(entityBody.getLinearVelocity().y) > 0) {
            return State.JUMPING;
        } else if (entityBody.getLinearVelocity().x > 0) {
            return State.RUNNING_RIGHT;
        } else if (entityBody.getLinearVelocity().x < 0) {
            return State.RUNNING_LEFT;
        } else {
            return State.STANDING;
        }
    }

    public void setSuperMario(boolean superMario) {
        this.redefineReady = true;
        this.superMario = superMario;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public GameData getGameData() {
        return gameData;
    }

    public boolean isSuperMario() {
        return superMario;
    }

    public HashMap<Integer, Boolean> getKeys() {
        return keys;
    }

}
