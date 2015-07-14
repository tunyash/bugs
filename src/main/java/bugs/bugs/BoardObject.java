package bugs.bugs;

/**
 * Created by tunyash on 7/11/15.
 * Static object on board
 */

abstract public class BoardObject {

    public BoardPosition[] getOccupied() {
        return occupied;
    }

    public void onBugStep(BoardPosition stepPos, Bug bug, Board board) {

    }

    public void onTimerTick(Board board) {

    }

    /**
     * @param pos should be one of occupied positions of object
     * @return true if pos should be obstacle, false if it is not
     */
    public boolean isObstacle(BoardPosition pos) {
        return false;
    }

    protected BoardPosition[] occupied;
}
