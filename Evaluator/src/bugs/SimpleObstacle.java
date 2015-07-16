package bugs;

import org.w3c.dom.Node;

/**
 * Created by tunyash on 7/11/15.
 */
public class SimpleObstacle extends BoardObject{
    public SimpleObstacle()
    {
        super(BoardObject.upperOrder);
    }
    public SimpleObstacle(BoardPosition pos)
    {
        super(BoardObject.upperOrder);
        this.occupied = new BoardPosition[1];
        occupied[0] = pos;
    }

    @Override
    public void loadMyselfFromNode(Node node) {
        int row = Integer.parseInt(node.getAttributes().getNamedItem("row").getNodeValue());
        int column = Integer.parseInt(node.getAttributes().getNamedItem("column").getNodeValue());

        occupied = new BoardPosition[1];
        occupied[0] = new BoardPosition(row, column);

    }

    @Override
    public String getType() {
        return "SimpleObstacle";
    }

    @Override
    public boolean isObstacle(BoardPosition pos) {
        //System.out.printf("%d %d ?? %d %d\n", pos.getRow(), pos.getColumn(), occupied[0].getRow(), occupied[0].getColumn());
        return (pos.compareTo(occupied[0]) == 0);
    }
    @Override
    public String toString()
    {
        return "#";
    }
}
