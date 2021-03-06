package bugs;

import org.w3c.dom.*;
import org.w3c.dom.Document;

import javax.swing.text.*;
import javax.swing.text.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by tunyash on 7/11/15.
 */


public class Board {

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        objects = new ArrayList<>();
        bugs = new ArrayList<>();
        cellObjects = new ArrayList[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                cellObjects[i][j] = new ArrayList<>();
        currentTime = 0;
        score = 0;
        lost = false;
    }

    public void addObject(BoardObject obj) {
        objects.add(obj);
        for (BoardPosition pos : obj.getOccupied()) {
            //  System.out.printf("%d %d\n", pos.getRow(), pos.getColumn());
            cellObjects[pos.getRow()][pos.getColumn()].add(obj);
        }
    }

    public void addBug(Bug bug) {
        bugs.add(bug);
    }

    public void addBug(BoardPosition pos, int color) {
        bugs.add(new Bug(pos, color));
    }

    public void removeInactiveObjects() {
        ArrayList<BoardObject> newObjects = new ArrayList<>();
        for (BoardObject object : objects) {
            if (object.isActive()) {
                newObjects.add(object);
            }
        }
        objects = new ArrayList<>();
        cellObjects = new ArrayList[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                cellObjects[i][j] = new ArrayList<>();
        for (BoardObject object: newObjects)
            addObject(object);
    }

    public boolean runOneRound() {
        if (lost) return false;
        for (Bug bug : bugs)
            bug.evaluateOrders(this);
        for (Bug bug : bugs)
            if (bug.getLifePoints() > 0) {
                for (BoardObject obj : cellObjects[bug.getCurrentPosition().getRow()][bug.getCurrentPosition().getColumn()])
                    if (obj.isActive())
                        obj.onBugStep(bug.getCurrentPosition(), bug, this);
            }
        for (BoardObject obj : objects)
            if (obj.isActive())
                obj.onTimerTick(this);
        currentTime++;
        return currentTime < IT_COUNT;
    }

    public void drawOneRound() {
        Integer[] ids = new Integer[objects.size()];
        for (int i = 0; i < ids.length; i++) ids[i] = i;
        Arrays.sort(ids, new Comparator<Integer>() {

            @Override
            public int compare(Integer t0, Integer t1) {
                if (objects.get(t0).getDisplayOrder() < objects.get(t1).getDisplayOrder()) return -1;
                if (objects.get(t0).getDisplayOrder() > objects.get(t1).getDisplayOrder()) return 1;
                return 0;
            }
        });
        for (int i = 0; i < ids.length; i++)
            if (objects.get(ids[i]).isActive())
                objects.get(ids[i]).notifyObserver();
        for (Bug bug : bugs) if (bug != null) bug.notifyObserver();
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<BoardObject> getObjects() {
        return objects;
    }

    public ArrayList<Bug> getBugs() {
        return bugs;
    }

    public boolean isCorrectPosition(BoardPosition pos) {
        return pos.getColumn() >= 0 && pos.getColumn() < width && pos.getRow() >= 0 && pos.getRow() < height;
    }

    public boolean isObstacle(BoardPosition pos) {
        if (!isCorrectPosition(pos)) throw new AssertionError();
        for (BoardObject obj : cellObjects[pos.getRow()][pos.getColumn()])
            if (obj.isActive() && obj.isObstacle(pos)) return true;
        return false;
    }

    @Override
    public String toString() {
        StringBuilder area = new StringBuilder();
        String[][] mArea = new String[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                mArea[i][j] = ".";
        for (BoardObject obj : objects)
            if (obj.isActive())
                for (BoardPosition pos : obj.getOccupied())
                    mArea[pos.getRow()][pos.getColumn()] = obj.toString();
        for (Bug bug : bugs)
            if (bug.getCurrentPosition() != null && bug.getLifePoints() > 0)
                mArea[bug.getCurrentPosition().getRow()][bug.getCurrentPosition().getColumn()] = bug.toString();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                area.append(mArea[i][j]);
            area.append("\n");
        }
        return area.toString();
    }


    public static Board loadFromFile(File file) throws Exception {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        DocumentBuilder builder = f.newDocumentBuilder();
        Document doc = builder.parse(file);
        Node root = doc.getFirstChild();
        return Board.loadFromNode(root);
    }

    public static Board loadFromNode(Node root) throws Exception {
        NamedNodeMap attr = root.getAttributes();
        int height = Integer.parseInt(attr.getNamedItem("height").getNodeValue());
        int width = Integer.parseInt(attr.getNamedItem("width").getNodeValue());
        //System.out.printf("%d %d\n", height, width);
        Board result = new Board(width, height);
        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            Node cur = root.getChildNodes().item(i);
            if (cur.getNodeName().equals("object")) {
                result.addObject(BoardObject.loadFromNode(cur));
            }
            if (cur.getNodeName().equals("bug")) {
                result.addBug(Bug.loadFromNode(cur));
            }

        }
        return result;
    }

    public Node saveToNode(Document document) throws Exception
    {
        this.removeInactiveObjects();
        Node el = document.createElement("board");
        Node width = document.createAttribute("width");
        width.setNodeValue(Integer.toString(this.width));
        el.getAttributes().setNamedItemNS(width);
        Node height = document.createAttribute("height");
        height.setNodeValue(Integer.toString(this.height));
        el.getAttributes().setNamedItemNS(height);

        System.out.println(document.getFirstChild());
        for (Bug bug:bugs)
            el.appendChild(bug.saveToNode(document));
        for (BoardObject object:objects)
            el.appendChild(object.saveToNode(document));

        return el;
    }

    public void saveToFile(String filename) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        document.appendChild(this.saveToNode(document));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);

        StreamResult result = new StreamResult(new FileOutputStream(new File(filename)));

        transformer.transform(source, result);
    }

    public ArrayList<BoardObject> getCellObjects(int row, int column) {
        return cellObjects[row][column];
    }


    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public static final int scoreForBug = 10;

    private int width;
    private int height;
    private int currentTime;


    private boolean lost;
    private final int IT_COUNT = 200;


    private int score;
    private ArrayList<BoardObject> objects;
    private ArrayList<Bug> bugs;
    private ArrayList<BoardObject>[][] cellObjects;
}
