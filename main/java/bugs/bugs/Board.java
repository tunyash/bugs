package bugs.bugs;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;

/**
 * Created by tunyash on 7/11/15.
 */


public class Board {

    public Board(GridLayout board, int width, int height) {
        this.board = board;
        this.width = width;
        this.height = height;
        objects = new ArrayList<BoardObject>();
        bugs = new ArrayList<Bug>();
        cellObjects = new ArrayList[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                cellObjects[i][j] = new ArrayList<Integer>();
        currentTime = 0;
        score = 0;
    }

    public void addObject(BoardObject obj) {
        int id = objects.size();
        objects.add(obj);
        for (BoardPosition pos : obj.getOccupied()) {
          //  System.out.printf("%d %d\n", pos.getRow(), pos.getColumn());
            cellObjects[pos.getRow()][pos.getColumn()].add(id);
        }
    }

    public void addBug(Bug bug) {
        bugs.add(bug);
    }

    public void addBug(BoardPosition pos, int color) {
        bugs.add(new Bug(BugAction.appear(pos), color));
    }

    public String[][] runGame() throws Exception {
            for (Bug bug : bugs)
                bug.evaluateOrders(this);
            for (Bug bug : bugs)
                if (bug.getLifePoints() > 0) {
                    for (Integer obj : cellObjects[bug.getCurrentPosition().getRow()][bug.getCurrentPosition().getColumn()])
                        objects.get(obj).onBugStep(bug.getCurrentPosition(), bug, this);
                }
            for (BoardObject obj : objects)
                obj.onTimerTick(this);

            Thread.currentThread().sleep(900);
            return makeString();
            //Runtime.getRuntime().exec("clear");
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

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<BoardObject> getObjects() {
        return objects;
    }

    public ArrayList<Bug> getBugs() {
        return bugs;
    }

    public boolean isCorrectPosition(BoardPosition pos) {
        return pos.getColumn() >= 0 && pos.getColumn() < width && pos.getRow() >= 0 && pos.getRow() < height;
    }

    public boolean isObstacle(BoardPosition pos) {
        if (!isCorrectPosition(pos)) throw new AssertionError();
        for (Integer obj : cellObjects[pos.getRow()][pos.getColumn()])
            if (objects.get(obj).isObstacle(pos)) return true;
        return false;
    }

    private String[][] makeString() {
        StringBuilder area = new StringBuilder();
        String[][] mArea = new String[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                mArea[i][j] = ".";
        for (BoardObject obj : objects)
            for (BoardPosition pos : obj.getOccupied())
                mArea[pos.getRow()][pos.getColumn()] = obj.toString();
        for (Bug bug : bugs)
            if (bug.getCurrentPosition() != null && bug.getLifePoints() > 0)
                mArea[bug.getCurrentPosition().getRow()][bug.getCurrentPosition().getColumn()] = bug.toString();
        return mArea;
    }

    public static final int scoreForBug = 10;

    private int width;
    private int height;
    private int currentTime;
    private final int IT_COUNT = 10;


    private GridLayout board;
    private int score;
    private ArrayList<BoardObject> objects;
    private ArrayList<Bug> bugs;
    private ArrayList<Integer>[][] cellObjects;
}
