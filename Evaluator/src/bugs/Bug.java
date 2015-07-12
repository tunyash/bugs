package bugs;

import java.util.ArrayList;

/**
 * Created by tunyash on 7/11/15.
 * Moving object on board
 */


public class Bug {


    public Bug(BugAction startAction, int color) {
        orders = new ArrayList<>();
        pushOrder(startAction);
        this.color = color;
    }
    public Bug(BugAction startAction) {
        orders = new ArrayList<>();
        pushOrder(startAction);
        color = -1;
    }

    public Bug(int color) {
        orders = new ArrayList<>();
        this.color = color;
    }

    public Bug() {
        orders = new ArrayList<>();
    }

    public BoardPosition getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(BoardPosition currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void pushOrder(BugAction order) {
        orders.add(order);
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public void evaluateOrders(Board board) {
        ArrayList<BugAction> restOrders = new ArrayList<>();
        for (BugAction act : orders)
            if (lifePoints > 0 && !act.evaluate(board, this)) restOrders.add(act);
        orders = restOrders;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private BoardPosition currentPosition;
    ArrayList<BugAction> orders;
    private int lifePoints;


    private int color;

}
