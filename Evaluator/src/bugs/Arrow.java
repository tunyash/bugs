package bugs;

/**
 * Created by tunyash on 7/12/15.
 */
public class Arrow extends BoardObject {
    public Arrow(BoardPosition pos, Direction direction)
    {
        occupied = new BoardPosition[1];
        occupied[0] = pos;
        this.direction = direction;
    }

    @Override
    public void onBugStep(BoardPosition stepPos, Bug bug, Board board) {
        bug.pushOrder(BugAction.forceToGo(direction));
    }

    private final Direction direction;
}
