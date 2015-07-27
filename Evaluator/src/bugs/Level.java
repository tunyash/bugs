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
        finalize = false;
    }

    public Level(Board board)
    {
        this.board = board;
        additionalObjects = new ArrayList<>();
        bugs = new ArrayList<>();
        finalize = false;
        available = new boolean[board.getHeight()][board.getWidth()];

    }

    void addBug(BoardPosition pos, Integer color)
    {
        if (finalize) throw new AssertionError();
        bugs.add(new Bug(pos, color));
    }

    void addBug(Bug bug)
    {
        if (finalize) throw new AssertionError();
        bugs.add(bug);
    }

    void addAditionalObject(String object, Integer count)
    {
        if (finalize) throw new AssertionError();
        additionalObjects.add(new Pair<>(object, count));
    }


    public boolean isAvaliable(int row, int column)
    {
        return available[row][column];
    }

    public Board getBoard() {
        return board;
    }

    public boolean isObjectAvailable(String name)
    {
        for (Pair<String,Integer> pair : additionalObjects)
            if (pair.first.equals(name) && pair.second.compareTo(0) == 1) return true;
        return false;
    }

    public void decrease(String name)
    {
        for (Pair<String,Integer> pair : additionalObjects)
            if (pair.first.equals(name)) pair.second -= 1;
    }

    public ArrayList<Pair<String, Integer>> getAdditionalObjects() {
        return additionalObjects;
    }

    public void setBugs(ArrayList<Bug> bugs) {
        if (finalize) throw new AssertionError();
        this.bugs = bugs;
    }


    public ArrayList<Bug> getBugs() {
        return bugs;
    }


    public static Level loadFromNode(Node node) throws Exception
    {
        Level result = new Level();
        for (int i = 0; i < node.getChildNodes().getLength(); i++)
        {

            Node cur = node.getChildNodes().item(i);
            System.out.println(cur.getNodeName());
            if (cur.getNodeName().equals("board"))
                result.setBoard(Board.loadFromNode(cur));
            if (cur.getNodeName().equals("bug")) {
                result.addBug(Bug.loadFromNode(cur));
            }
            if (cur.getNodeName().equals("objectType"))
            {
                int count = Integer.parseInt(cur.getAttributes().getNamedItem("count").getNodeValue());
                result.addAditionalObject(cur.getAttributes().getNamedItem("name").getNodeValue(), count);
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

                        int row = Integer.parseInt(subcur.getAttributes().getNamedItem("row").getNodeValue());
                        int column = Integer.parseInt(subcur.getAttributes().getNamedItem("column").getNodeValue());
                        System.out.printf("%d %d\n", row, column);
                        result.available[row][column] = true;
                    }
                }
            }
        }
        result.setFinalize(true);
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

    public boolean isFinalize() {
        return finalize;
    }

    public void setFinalize(boolean finalize) {
        this.finalize = finalize;
    }


    //TODO: don't do it like that, available should be independent from board, but it should be asserted that width and height are same
    public void setBoard(Board board) {

        this.board = board;
        if (available == null) available = new boolean[board.getHeight()][board.getWidth()];
    }

    private Board board;



    private boolean finalize;
    private ArrayList<Pair<String, Integer>> additionalObjects;
    private ArrayList<Bug> bugs;
    private boolean[][] available;

}


