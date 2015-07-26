package bugs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

class Surface extends JPanel {
    public Surface(Level level)
    {
        super();
        this.level = level;
    }
    public Level level;
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = this.getWidth();
        int height = this.getHeight();
        Board area = level.getBoard();
        int maxAreaWidth = (int)((double)width * 0.8);
        int maxAreaHeight = (int)((double)height* 0.9);
        maxAreaHeight = Math.min(maxAreaHeight, maxAreaWidth * area.getHeight()/ area.getWidth());
        maxAreaWidth = Math.min(maxAreaWidth, maxAreaHeight * area.getWidth() / area.getHeight());
        int lx = (int)((double)width * 0.05);
        int ly = height / 2 - maxAreaHeight / 2;
        int cellSide = Math.min(maxAreaWidth / area.getWidth(), maxAreaHeight / area.getHeight());
        for (int i = 0; i < area.getHeight(); i++)
            for (int j = 0; j < area.getWidth(); j++)
            {
                g2d.drawRect(lx + cellSide * j, ly + cellSide * i, cellSide, cellSide);

               // g2d.drawString(area.area[i][j], lx + cellSide * j, ly + cellSide * i + 10);
            }
        for (BoardObject obj: area.getObjects()) {
            GraphicsBoardObjectDrawer some = (GraphicsBoardObjectDrawer) obj.getObserver();
            if (some != null) some.doDrawing(g2d, lx, ly, cellSide);
        }
        for (Bug bug: area.getBugs())
        {
            GraphicsBugDrawer draw = (GraphicsBugDrawer) bug.getObserver();
            if (draw != null) draw.doDrawing(g2d, lx, ly, cellSide);
        }
    }
}


abstract class GraphicsBoardObjectDrawer extends BoardObjectDrawer
{
    public GraphicsBoardObjectDrawer(BoardObject object)
    {
        super(object);
    }

    abstract public void doDrawing(Graphics2D surface, int cornerX, int cornerY, int cellSide);
}

class GraphicsSimpleObstacleDrawer extends GraphicsBoardObjectDrawer
{

    public GraphicsSimpleObstacleDrawer(SimpleObstacle object) {
        super(object);
    }

    @Override
    public void redraw() {

    }

    @Override
    public void doDrawing(Graphics2D surface, int cornerX, int cornerY, int cellSide) {
        int row = observable.getOccupied()[0].getRow();
        int column = observable.getOccupied()[0].getColumn();
        surface.fillRect(cornerX + column * cellSide, cornerY + row * cellSide, cellSide, cellSide);
    }
}

class GraphicsArrowDrawer extends GraphicsBoardObjectDrawer
{
    public GraphicsArrowDrawer(Arrow object) {
        super(object);
    }

    @Override
    public void redraw() {

    }

    @Override
    public void doDrawing(Graphics2D surface, int cornerX, int cornerY, int cellSide) {
        Arrow obs = (Arrow)observable;
        int row = obs.getOccupied()[0].getRow();
        int column = obs.getOccupied()[0].getColumn();
        int cellX = cornerX + column * cellSide;
        int cellY = cornerY + row * cellSide;
        try {
            BufferedImage img = ImageIO.read(new File("right.png"));
            AffineTransform tr = AffineTransform.getRotateInstance(Math.toRadians(90), img.getWidth() / 2, img.getHeight() / 2);
            AffineTransformOp op = new AffineTransformOp(tr, AffineTransformOp.TYPE_BILINEAR);
            int cnt = 0;
            if (obs.getDirection() == Direction.RIGHT) cnt = 0;
            if (obs.getDirection() == Direction.DOWN) cnt = 1;
            if (obs.getDirection() == Direction.LEFT) cnt = 2;
            if (obs.getDirection() == Direction.UP) cnt = 3;
            for (int i = 0; i < cnt; i++) img = op.filter(img, null);
            surface.drawImage(img, cellX, cellY, cellSide, cellSide, null);
        } catch (Exception e)
        {
            System.out.print("not loaded");

        }
    }
}


class GraphicsTrapDrawer extends GraphicsBoardObjectDrawer
{
    public GraphicsTrapDrawer(Trap object) {
        super(object);
    }

    @Override
    public void redraw() {

    }

    @Override
    public void doDrawing(Graphics2D surface, int cornerX, int cornerY, int cellSide) {
        Trap obs = (Trap)observable;
        int row = obs.getOccupied()[0].getRow();
        int column = obs.getOccupied()[0].getColumn();
        int cellX = cornerX + column * cellSide;
        int cellY = cornerY + row * cellSide;
        try {
            BufferedImage img = ImageIO.read(new File("trap.png"));
            WritableRaster raster = img.getRaster();
            for (int i = 0; i < raster.getHeight(); i++)
                for (int j = 0; j < raster.getWidth(); j++)
                {
                    int[] px = raster.getPixel(j,i, (int[]) null);
                    px[0] = (int)((double)BugColor.byNumber(obs.getColor()).getRed()*((double)px[0]/255));
                    px[1] = (int)((double)BugColor.byNumber(obs.getColor()).getGreen()*((double)px[1]/255));
                    px[2] = (int)((double)BugColor.byNumber(obs.getColor()).getBlue()*((double)px[2]/255));

                    raster.setPixel(j,i, px);
                }
            surface.drawImage(img, cellX, cellY, cellSide, cellSide, null);
        } catch (Exception e)
        {
            System.out.print("not loaded");

        }
    }
}


class GraphicsBugDrawer extends BugDrawer
{

    public GraphicsBugDrawer(Bug bug)
    {
        super(bug);
    }
    @Override
    public void redraw() {

    }

    public void doDrawing(Graphics2D surface, int cornerX, int cornerY, int cellSide) {
        if (observable.getLifePoints() <= 0 || observable.getCurrentPosition() == null) return;
        int row = observable.getCurrentPosition().getRow();
        int column = observable.getCurrentPosition().getColumn();
        int cellX = cornerX + column * cellSide;
        int cellY = cornerY + row * cellSide;
        try {
            BufferedImage img = ImageIO.read(new File("bug.png"));
            WritableRaster raster = img.getRaster();
            for (int i = 0; i < raster.getHeight(); i++)
                for (int j = 0; j < raster.getWidth(); j++)
                {

                    int[] px = raster.getPixel(j,i, (int[]) null);

                    px[0] = (int)((double)BugColor.byNumber(observable.getColor()).getRed()*((double)px[0]/255));
                    px[1] = (int)((double)BugColor.byNumber(observable.getColor()).getGreen()*((double)px[1]/255));
                    px[2] = (int)((double)BugColor.byNumber(observable.getColor()).getBlue()*((double)px[2]/255));


                    raster.setPixel(j,i, px);
                }
            surface.drawImage(img, cellX, cellY, cellSide, cellSide, null);
        } catch (Exception e)
        {
            System.out.print("not loaded");

        }
    }
}

class Button
{
    EditorTool tool;
    GameInterface gameInterface;

}

class MainForm extends JFrame
{
    public MainForm(Level area)
    {
        super();
        add(new Surface(area));
        setTitle("Bugs");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}



public class Main {

    public static void main(String[] args) throws Exception {
        // write your code here
        //Runtime.getRuntime().exit(0);

        // System.out.println(Runtime.getRuntime().availableProcessors());
      /*  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document doc = builder.parse(ClassLoader.getSystemResourceAsStream("round1.xml"));
*/
        Level myLevel = Level.loadFromFile(new File("round1.xml"));
        Board myBoard = myLevel.getBoard();
        MainForm form = new MainForm(myLevel);
        form.setVisible(true);


        for (BoardObject object: myBoard.getObjects()) {
            if (object.getType().equals("Arrow")) new GraphicsArrowDrawer((Arrow)object);
            else if (object.getType().equals("Trap")) new GraphicsTrapDrawer((Trap)object);
            else if (object.getType().equals("SimpleObstacle")) new GraphicsSimpleObstacleDrawer((SimpleObstacle)object);
        }
        for (Bug bug: myBoard.getBugs()) {
            new GraphicsBugDrawer(bug);

        }


        //myBoard.runOneRound();
        //while (myBoard.runOneRound()) {
           // area.clear();
            myBoard.drawOneRound();
           // System.out.print(area);
            form.repaint();
            Thread.currentThread().sleep(800);
        //}
        System.out.printf("Score: %d\n", myBoard.getScore());
    }



}
