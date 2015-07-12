package bugs;

/**
 * Created by tunyash on 7/11/15.
 */
public class SimpleObstacle extends BoardObject{
    public SimpleObstacle(BoardPosition pos)
    {
        this.occupied = new BoardPosition[1];
        occupied[0] = pos;
    }
    @Override
    public boolean isObstacle(BoardPosition pos) {
        return (pos.compareTo(occupied[0]) == 0);
    }
}
