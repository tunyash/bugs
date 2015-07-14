package bugs;

/**
 * Created by tunyash on 7/12/15.
 */
public class Trap extends BoardObject {
    public Trap(BoardPosition pos, int color)
    {
        super(BoardObject.upperOrder);
        this.color = color;
        occupied = new BoardPosition[1];
        occupied[0] = pos;
    }

    public int getColor() {
        return color;
    }

    @Override
    public void onBugStep(BoardPosition stepPos, Bug bug, Board board) {
        if (bug.getColor() == color) {
            bug.pushOrder(BugAction.kill());
            board.setScore(board.getScore() + Board.scoreForBug);
        }
    }

    private final int color;

    @Override
    public String toString()
    {
        return ColoredString.getColored(color,"T");
    }
}
