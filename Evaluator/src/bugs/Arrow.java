package bugs;

/**
 * Created by tunyash on 7/12/15.
 */
public class Arrow extends BoardObject {
    public Arrow(BoardPosition pos, Direction direction)
    {
        super(BoardObject.floorOrder);
        occupied = new BoardPosition[1];
        occupied[0] = pos;
        this.direction = direction;
    }

    @Override
    public void onBugStep(BoardPosition stepPos, Bug bug, Board board) {
        //System.out.println("Bug on the arrow");
        bug.pushOrder(BugAction.forceToGo(direction));
    }

    private final Direction direction;

    @Override
    public String toString()
    {
        if (direction == Direction.DOWN) return "v";
        if (direction == Direction.UP) return "^";
        if (direction == Direction.LEFT) return "<";
        return ">";
    }
}
