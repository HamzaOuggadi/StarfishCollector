package net.hamzaouggadi.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class Scene extends Actor {

    private ArrayList<SceneSegment> segmentList;
    private int index;


    public Scene() {
        super();
        segmentList = new ArrayList<>();
        index = -1;
    }


    public void addSegment(SceneSegment segment) {
        segmentList.add(segment);
    }

    public void start() {
        index = 0;
        segmentList.get(index).start();
    }

    @Override
    public void act(float delta) {
        if (isSegmentFinished() && !isLastSegment()) {
            loadNextSegment();
        }
    }

    public boolean isSegmentFinished() {
        return segmentList.get(index).isFinished();
    }

    public boolean isLastSegment() {
        return (index >= segmentList.size() - 1);
    }

    public void loadNextSegment() {

        if (isLastSegment()) {
            return;
        }

        segmentList.get(index).finish();
        index++;
        segmentList.get(index).start();
    }

    public boolean isSceneFinished() {
        System.out.println("is Scene finished  ? : " + (isLastSegment() && isSegmentFinished()));
        System.out.println("is last segment : " + isLastSegment());
        System.out.println("is Segment Finished : " + isSegmentFinished());
        return (isLastSegment() && isSegmentFinished());
    }

}
