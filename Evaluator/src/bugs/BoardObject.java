package bugs;

/**
 * Created by tunyash on 7/11/15.
 * Static object on board
 */
abstract public class BoardObject {
    public BoardPosition[] getOccupied() {
        return occupied;
    }

    abstract public void onBugStep(BoardPosition stepPos, Bug bug, Board board);
    abstract public void onTimerTick(Board board);
    abstract public boolean isObstacle(BoardPosition pos);

    private BoardPosition[] occupied;
}
