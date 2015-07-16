package bugs;

import org.w3c.dom.Node;

/**
 * Created by tunyash on 7/12/15.
 */
public class Arrow extends BoardObject {
    public Arrow()
    {
        super(BoardObject.floorOrder);
    }
    public Arrow(BoardPosition pos, Direction direction)
    {
        super(BoardObject.floorOrder);
        occupied = new BoardPosition[1];
        occupied[0] = pos;
        this.direction = direction;
    }

    @Override
    public void loadMyselfFromNode(Node node) {
        int row = Integer.parseInt(node.getAttributes().getNamedItem("row").getNodeValue());
        int column = Integer.parseInt(node.getAttributes().getNamedItem("column").getNodeValue());

        occupied = new BoardPosition[1];
        occupied[0] = new BoardPosition(row, column);
        String sDirection = node.getAttributes().getNamedItem("direction").getNodeValue();
        for (Direction dir : Direction.values()) {
           // System.out.printf("%s ? %s\n", dir.toString(), sDirection);
            if (dir.toString().equals(sDirection)) this.direction = dir;
        }
        assert (this.direction != null);
    }

    @Override
    public String getType() {
        return "Arrow";
    }

    @Override
    public void onBugStep(BoardPosition stepPos, Bug bug, Board board) {
        //System.out.println("Bug on the arrow");
        bug.pushOrder(BugAction.forceToGo(direction));
    }

    public Direction getDirection() {
        return direction;
    }



    private Direction direction;

    @Override
    public String toString()
    {
        if (direction == Direction.DOWN) return "v";
        if (direction == Direction.UP) return "^";
        if (direction == Direction.LEFT) return "<";
        return ">";
    }
}
