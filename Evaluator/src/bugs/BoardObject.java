package bugs;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by tunyash on 7/11/15.
 * Static object on board
 */

abstract public class BoardObject {

    public BoardObject(int displayOrder) {
        this.displayOrder = displayOrder;
        this.active = true;
    }

    public BoardPosition[] getOccupied() {
        return occupied;
    }

    public abstract void loadMyselfFromNode(Node node);
    public abstract String getType();

    public void onBugStep(BoardPosition stepPos, Bug bug, Board board) {
        // do nothing
    }

    public void onTimerTick(Board board) {
        // do nothing
    }


    /**
     * @param pos should be one of occupied positions of object
     * @return true if pos should be obstacle, false if it is not
     */
    public boolean isObstacle(BoardPosition pos) {
        return false;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public BoardObjectDrawer getObserver() {
        return observer;
    }

    public static BoardObject loadFromNode(Node node) throws Exception {
        String className = node.getAttributes().getNamedItem("type").getNodeValue();
        Class obj = Class.forName("bugs." + className);
        BoardObject realObject = (BoardObject)obj.newInstance();
        realObject.loadMyselfFromNode(node);
        return realObject;
        //return null;
    }

    public abstract Node saveToNode(Document document) throws Exception;
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
    public void setObserver(BoardObjectDrawer observer) {
        this.observer = observer;
    }

    public void notifyObserver() {
        if (observer != null) observer.redraw();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static int upperOrder = 1;
    public static int floorOrder = 0;

    protected BoardPosition[] occupied;
    protected int displayOrder;



    protected boolean active;
    protected BoardObjectDrawer observer;
}
