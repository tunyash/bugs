package bugs;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tunyash on 7/11/15.
 * Moving object on board
 */


public class Bug {


    public Bug(BugAction startAction, int color) {
        orders = new ArrayList<>();
        lifePoints = 100;
        pushOrder(startAction);
        this.color = color;
    }
    public Bug(BugAction startAction) {
        orders = new ArrayList<>();
        lifePoints = 100;
        pushOrder(startAction);
        color = -1;
    }

    public Bug(int color) {
        orders = new ArrayList<>();
        this.color = color;
        lifePoints = 100;
    }

    public Bug() {
        orders = new ArrayList<>();
        lifePoints = 100;
        color = -1;
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
        //System.out.println("Evaluating orders");
        ArrayList<BugAction> restOrders = new ArrayList<>();
        Collections.sort(restOrders);
        for (BugAction act : orders)
            if (lifePoints > 0 && !act.evaluate(board, this)) restOrders.add(act);
        orders = restOrders;
        if (movementActive)
        {
            moveBug(board, movementDirection);
        }
    }

    public void moveBug(Board board, Direction direction)
    {
        BoardPosition newPos = this.getCurrentPosition().move(direction);
        // System.out.println(board.isCorrectPosition(newPos));
        if (!board.isCorrectPosition(newPos)) return;
        // System.out.println(board.isObstacle(newPos));
        if (board.isObstacle(newPos)) return;
        this.setCurrentPosition(newPos);
        return;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isMovementActive() {
        return movementActive;
    }

    public void setMovementActive(boolean movementActive) {
        this.movementActive = movementActive;
    }

    public Direction getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(Direction movementDirection) {
        this.movementDirection = movementDirection;
    }

    public BugDrawer getObserver() {
        return observer;
    }

    public void setObserver(BugDrawer observer) {
        this.observer = observer;
    }

    public void notifyObserver() {
        if (observer != null) observer.redraw();
    }

    @Override
    public String toString()
    {
        return ColoredString.getColored(this.color,"B");
    }

    private BoardPosition currentPosition;
    ArrayList<BugAction> orders;
    private int lifePoints;




    private boolean movementActive = false;
    private Direction movementDirection;

    private BugDrawer observer;
    private int color;

}
