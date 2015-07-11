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

    private int width;
    private int height;

    public int getCurrentTime() {
        return currentTime;
    }

    private int currentTime;
    private ArrayList<BoardObject> objects;
    private ArrayList<Bug> bugs;
    private ArrayList<Integer>[][] cellObjects;
}
