package bugs;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Created by tunyash on 7/12/15.
 */
public class Trap extends BoardObject {
    public Trap()
    {
        super(BoardObject.upperOrder);
        this.color = -1;
    }
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
    public void loadMyselfFromNode(Node node) {
        //System.out.println("loaded Trap");
        int row = Integer.parseInt(node.getAttributes().getNamedItem("row").getNodeValue());
        int column = Integer.parseInt(node.getAttributes().getNamedItem("column").getNodeValue());
        int color = Integer.parseInt(node.getAttributes().getNamedItem("color").getNodeValue());
        this.color = color;
        occupied = new BoardPosition[1];
        occupied[0] = new BoardPosition(row, column);
    }

    @Override
    public String getType() {
        return "Trap";
    }

    @Override
    public void onBugStep(BoardPosition stepPos, Bug bug, Board board) {
        if (bug.getColor() == color) {
            bug.pushOrder(BugAction.kill());
            board.setScore(board.getScore() + Board.scoreForBug);
        }
        else
        {
            board.setScore(0);
            board.setLost(true);
        }
    }

    @Override
    public Node saveToNode(Document document) throws Exception {
        Node el = document.createElement("object");
        Node type = document.createAttribute("type");
        type.setNodeValue(this.getType());
        el.getAttributes().setNamedItemNS(type);
        Node row = document.createAttribute("row");
        row.setNodeValue(Integer.toString(this.occupied[0].getRow()));
        el.getAttributes().setNamedItemNS(row);
        Node column = document.createAttribute("column");
        column.setNodeValue(Integer.toString(this.occupied[0].getColumn()));
        el.getAttributes().setNamedItemNS(column);
        Node color = document.createAttribute("color");
        color.setNodeValue(Integer.toString(this.color));
        el.getAttributes().setNamedItemNS(color);
        return el;
    }

    private int color;

    @Override
    public String toString()
    {
        return ColoredString.getColored(color,"T");
    }
}
