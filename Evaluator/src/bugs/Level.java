package bugs;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by tunyash on 7/17/15.
 */
public class Level {


    public Level() {
        additionalObjects = new ArrayList<>();
        bugs = new ArrayList<>();
    }

    public Level(Board initial)
    {
        this.initial = initial;
        additionalObjects = new ArrayList<>();
        bugs = new ArrayList<>();
    }

    void addBug(BoardPosition pos, Integer color)
    {
        bugs.add(new Pair<>(pos, color));
    }

    void addAditionalObject(BoardObjectConstructor object, Integer count)
    {
        additionalObjects.add(new Pair<>(object, count));
    }

    public Board getInitial() {
        return initial;
    }

    public void setInitial(Board initial) {
        this.initial = initial;
    }

    public ArrayList<Pair<BoardObjectConstructor, Integer>> getAdditionalObjects() {
        return additionalObjects;
    }



    public void setBugs(ArrayList<Pair<BoardPosition, Integer>> bugs) {
        this.bugs = bugs;
    }


    public ArrayList<Pair<BoardPosition, Integer>> getBugs() {
        return bugs;
    }


    public static Level loadFromNode(Node node) throws Exception
    {
        Level result = new Level();
        for (int i = 0; i < node.getChildNodes().getLength(); i++)
        {
            Node cur = node.getChildNodes().item(i);
            if (cur.getNodeName().equals("board"))
                result.initial = Board.loadFromNode(cur);
            if (cur.getNodeName().equals("bug")) {
                int row = Integer.parseInt(cur.getAttributes().getNamedItem("row").getNodeValue());
                int column = Integer.parseInt(cur.getAttributes().getNamedItem("column").getNodeValue());
                int color = Integer.parseInt(cur.getAttributes().getNamedItem("color").getNodeValue());
                result.addBug(new BoardPosition(row, column), color);
            }
            if (cur.getNodeName().equals("objectConstructor"))
            {
                int count = Integer.parseInt(cur.getAttributes().getNamedItem("count").getNodeValue());
                result.addAditionalObject(BoardObjectConstructor.loadFromNode(cur), count);
            }
            if (cur.getNodeName().equals("available"))
            {
                int width = Integer.parseInt(cur.getAttributes().getNamedItem("width").getNodeValue());
                int height = Integer.parseInt(cur.getAttributes().getNamedItem("height").getNodeValue());
                result.available = new boolean[height][width];
                for (int y = 0; y < height; y++)
                    for (int x = 0; x < width; x++)
                        result.available[y][x] = false;
                for (int j = 0; j < cur.getChildNodes().getLength(); j++)
                {
                    Node subcur = cur.getChildNodes().item(j);
                    if (subcur.getNodeName().equals("position"))
                    {
                        int row = Integer.parseInt(cur.getAttributes().getNamedItem("row").getNodeValue());
                        int column = Integer.parseInt(cur.getAttributes().getNamedItem("column").getNodeValue());
                        result.available[row][column] = true;
                    }
                }
            }
        }
        return result;
    }

    public static Level loadFromFile(File file) throws Exception
    {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        DocumentBuilder builder = f.newDocumentBuilder();
        Document doc = builder.parse(file);
        Node root = doc.getFirstChild();
        return Level.loadFromNode(root);
    }

    private Board initial;
    private ArrayList<Pair<BoardObjectConstructor, Integer>> additionalObjects;
    private ArrayList<Pair<BoardPosition, Integer>> bugs;
    private boolean[][] available;

}


