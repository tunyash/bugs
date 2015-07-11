package bugs;

import java.util.ArrayList;

/**
 * Created by tunyash on 7/11/15.
 * Moving object on board
 */
public class Bug {
    public Bug(BugAction startAction)
    {
        orders = new ArrayList<>();
        pushOrder(startAction);
    }

    public Bug()
    {
        orders = new ArrayList<>();
    }

    public BoardPosition getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(BoardPosition currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void pushOrder(BugAction order)
    {
        orders.add(order);
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public void evaluateOrders(Board board)
    {
        ArrayList<BugAction> restOrders = new ArrayList<>();
        for (BugAction act : orders)
            if (!act.evaluate(board, this)) restOrders.add(act);
        orders = restOrders;
    }

    private BoardPosition currentPosition;
    ArrayList<BugAction> orders;
    private int lifePoints;

}
