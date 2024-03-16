package net.hamzaouggadi.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class BaseActor extends Actor {

    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean isAnimationPaused;
    private Vector2 velocityVec;
    private Vector2 accelerationVec;
    private float acceleration;
    private float maxSpeed;
    private float deceleration;
    private Polygon boundaryPolygon;


    public BaseActor(float x, float y, Stage stage) {
        super();

        animation = null;
        elapsedTime = 0f;
        isAnimationPaused = false;
        velocityVec = new Vector2(0f,0f);
        accelerationVec = new Vector2(0f,0f);
        acceleration = 0f;
        maxSpeed = 1000f;
        deceleration = 0f;

        setPosition(x, y);
        stage.addActor(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!isAnimationPaused) {
            elapsedTime += delta;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a);

        if (animation != null && isVisible()) {
            batch.draw(animation.getKeyFrame(elapsedTime),
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation());
        }
    }

    public void applyPhysics(float dt) {

        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);

        float speed = getSpeed();

        // Decrease speed when not accelerating
        if (accelerationVec.len() == 0) {
            speed -= deceleration * dt;
        }

        speed = MathUtils.clamp(speed, 0, maxSpeed);

        setSpeed(speed);

        moveBy(velocityVec.x * dt, velocityVec.y * dt);

        accelerationVec.set(0,0);
    }

    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop) {

        Array<TextureRegion> textureArray = new Array<>();

        for (String fileName : fileNames) {
            Texture texture = new Texture(Gdx.files.internal(fileName));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if (loop) {
            anim.setPlayMode(Animation.PlayMode.LOOP);
        } else {
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }

        if (animation == null) {
            setAnimation(anim);
        }

        return anim;
    }

    public Animation<TextureRegion> loadAnimationFromSheet(String fileName,
                                                           int rows, int columns,
                                                           float frameDuration, boolean loop) {

        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int frameWidth = texture.getWidth() / columns;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<>();

        for (int r=0; r<rows; r++) {
            for (int c=0; c<columns; c++) {
                textureArray.add(temp[r][c]);
            }
        }

        Animation<TextureRegion> anim = new Animation<>(frameDuration, textureArray);

        if (loop) {
            anim.setPlayMode(Animation.PlayMode.LOOP);
        } else {
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }

        if (animation == null) {
            setAnimation(anim);
        }

        return anim;
    }

    public Animation<TextureRegion> loadTexture(String fileName) {
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
        TextureRegion textureRegion = animation.getKeyFrame(0);
        float width = textureRegion.getRegionWidth();
        float height = textureRegion.getRegionHeight();
        setSize(width, height);
        setOrigin(width/2f, height/2f);

        if (boundaryPolygon == null) {
            setBoundaryPolygon();
        }
    }

    public void setAnimationPaused(boolean animationPaused) {
        isAnimationPaused = animationPaused;
    }

    public void setSpeed(float speed) {
        if (velocityVec.len() == 0) {
            velocityVec.set(speed, 0);
        } else {
            velocityVec.setLength(speed);
        }
    }

    public float getSpeed() {
        return velocityVec.len();
    }

    public void setMotionAngle(float angle) {
        velocityVec.setAngleDeg(angle);
    }

    public float getMotionAngle() {
        return velocityVec.angleDeg();
    }

    public boolean isMoving() {
        return (getSpeed() > 0);
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void accelerateAtAngle(float angle) {
        accelerationVec.add(new Vector2(acceleration, 0).setAngleDeg(angle));
    }

    public void accelerateForward() {
        accelerateAtAngle(getRotation());
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    public void setBoundaryPolygon() {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0,0, w,0, w,h, 0,h};
        boundaryPolygon = new Polygon(vertices);
    }

    public void setBoundaryPolygon(int numSides) {
        float w = getWidth();
        float h = getHeight();

        float[] vertices = new float[2*numSides];

        for (int i=0; i<numSides; i++) {
            float angle = i * 6.28f / numSides;

            // x coordinates
            vertices[2*i] = w/2 * MathUtils.cos(angle) + w/2;
            // y coordinates
            vertices[2*i + 1] = h/2 * MathUtils.sin(angle) + h/2;
        }

        boundaryPolygon = new Polygon(vertices);
    }

    public Polygon getBoundaryPolygon() {
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());
        return boundaryPolygon;
    }

    public boolean overlaps(BaseActor other) {
        Polygon polygon1 = this.getBoundaryPolygon();
        Polygon polygon2 = other.getBoundaryPolygon();

        // Initial test to improve performance
        if (!polygon1.getBoundingRectangle().overlaps(polygon2.getBoundingRectangle())) {
            return false;
        }

        return Intersector.overlapConvexPolygons(polygon1, polygon2);
    }

    public void centerAtPosition(float x, float y) {
        setPosition(x - getWidth()/2, y - getHeight()/2);
    }

    public void centerAtActor(BaseActor other) {
        centerAtPosition(other.getX() + other.getWidth()/2, other.getY() + other.getHeight()/2);
    }

    public void setOpacity(float opacity) {
        this.getColor().a = opacity;
    }

    public Vector2 preventOverlap(BaseActor other) {
        Polygon polygon1 = this.getBoundaryPolygon();
        Polygon polygon2 = other.getBoundaryPolygon();

        // Initial test to improve performance
        if (!polygon1.getBoundingRectangle().overlaps(polygon2.getBoundingRectangle())) {
            return null;
        }

        MinimumTranslationVector mtv = new MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(polygon1, polygon2, mtv);

        if (!polygonOverlap) {
            return null;
        }

        this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
        return mtv.normal;
    }

    public static ArrayList<BaseActor> getList(Stage stage, String className) {
        ArrayList<BaseActor> list = new ArrayList<>();

        Class theClass = null;

        try {
            theClass = Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Actor a : stage.getActors()) {
            if (theClass.isInstance(a)) {
                list.add((BaseActor) a);
            }
        }
        return list;
    }

    public static int count(Stage stage, String className) {
        return getList(stage, className).size();
    }


}
