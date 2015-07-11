package bugs;

import java.util.ArrayList;

/**
 * Created by tunyash on 7/11/15.
 */
public class Board {

    public Board(int width, int height)
    {
        this.width = width;
        this.height = height;
        objects = new ArrayList<>();
        bugs = new ArrayList<>();
        cellObjects = new ArrayList[width][height];
        currentTime = 0;
        score = 0;
    }

    public void addObject(BoardObject obj)
    {
        int id = objects.size();
        objects.add(obj);
        for (BoardPosition pos : obj.getOccupied())
            cellObjects[pos.getRow()][pos.getColumn()].add(id);
    }

    public void addBug(BugAction startAction)
    {
        bugs.add(new Bug(startAction));
    }

    public void runGame()
    {
        for (int it = 0; it < IT_COUNT; it++)
        {
            for (Bug bug : bugs)
                bug.evaluateOrders(this);
            for (Bug bug : bugs)
                if (bug.getLifePoints() > 0) {
                    for (Integer obj : cellObjects[bug.getCurrentPosition().getRow()][bug.getCurrentPosition().getColumn()])
                        objects.get(obj).onBugStep(bug.getCurrentPosition(), bug, this);
                }
            for (BoardObject obj : objects)
                obj.onTimerTick(this);
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public int getCurrentTime() {
        return currentTime;
    }

    public int getScore() {
        return score;
    }




    private int width;
    private int height;


    private int currentTime;

    private final int IT_COUNT = 200;
    private int score;
    private ArrayList<BoardObject> objects;
    private ArrayList<Bug> bugs;
    private ArrayList<Integer>[][] cellObjects;
}
