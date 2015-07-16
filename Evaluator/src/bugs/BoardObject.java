package bugs;

/**
 * Created by tunyash on 7/11/15.
 * Static object on board
 */

abstract public class BoardObject {


    public BoardObject(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public BoardPosition[] getOccupied() {
        return occupied;
    }

    public void onBugStep(BoardPosition stepPos, Bug bug, Board board) {
        // do nothing
    }

    public void onTimerTick(Board board) {
        // do nothing
    }


    /**
     * @param pos should be one of occupied positions of object
     * @return true if pos should be obstacle, false if it is not
     */
    public boolean isObstacle(BoardPosition pos) {
        return false;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public BoardObjectDrawer getObserver() {
        return observer;
    }

    public void setObserver(BoardObjectDrawer observer) {
        this.observer = observer;
    }

    public void notifyObserver() {
        if (observer != null) observer.redraw();
    }

    public static int upperOrder = 1;
    public static int floorOrder = 0;

    protected BoardPosition[] occupied;
    protected int displayOrder;
    protected BoardObjectDrawer observer;
}
