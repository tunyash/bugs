package bugs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Surface extends JPanel {
    public Surface(final GameInterface gameInterface, final ArrayList<Button> buttons, final JFrame pane) {
        super();
        this.gameInterface = gameInterface;
        this.pane = pane;
        this.buttons = buttons;
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                recalc();
                Board board = gameInterface.getLevel().getBoard();
                if (mouseEvent.getX() >= lx && mouseEvent.getX() <= lx + cellSide * board.getWidth()
                        && mouseEvent.getY() >= ly && mouseEvent.getY() <= ly + cellSide * board.getHeight())
                {
                    gameInterface.boardClick((mouseEvent.getY()-ly)/cellSide,(mouseEvent.getX()-lx)/cellSide);
                }
                for (int i = 0; i < buttons.size(); i++)
                    if (mouseEvent.getX() >= blx && mouseEvent.getX() <= blx + cellSide
                            && mouseEvent.getY() >= ly + i*cellSide && mouseEvent.getY() < ly + (i+1)*cellSide) {
                        for (Button button2: buttons)
                            if (button2 != buttons.get(i)) button2.setState(0);
                        buttons.get(i).onClick();
                    }

                for (BoardObject object : board.getObjects()) {
                    if (object.getObserver() != null) continue;
                    if (object.getType().equals("Arrow")) new GraphicsArrowDrawer((Arrow) object);
                    else if (object.getType().equals("Trap")) new GraphicsTrapDrawer((Trap) object);
                    else if (object.getType().equals("SimpleObstacle"))
                        new GraphicsSimpleObstacleDrawer((SimpleObstacle) object);
                }
                pane.repaint(0,0,0,getWidth(),getHeight());
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    public GameInterface gameInterface;
    ArrayList<Button> buttons;
    int lx,ly,blx,cellSide;
    final JFrame pane;
    synchronized void recalc()
    {
        Level level = gameInterface.getLevel();
        int width = this.getWidth();
        int height = this.getHeight();
        Board area = level.getBoard();
        int maxAreaWidth = (int) ((double) width * 0.8);
        int maxAreaHeight = (int) ((double) height * 0.9);
        maxAreaHeight = Math.min(maxAreaHeight, maxAreaWidth * area.getHeight() / area.getWidth());
        maxAreaWidth = Math.min(maxAreaWidth, maxAreaHeight * area.getWidth() / area.getHeight());
        lx = (int) ((double) width * 0.05);
        ly = height / 2 - maxAreaHeight / 2;
        cellSide = Math.min(maxAreaWidth / area.getWidth(), maxAreaHeight / area.getHeight());
        blx = (width + lx + maxAreaWidth) / 2 - cellSide / 2;

    }

    @Override
    synchronized public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        recalc();
        Board area = gameInterface.getLevel().getBoard();

        for (int i = 0; i < area.getHeight(); i++)
            for (int j = 0; j < area.getWidth(); j++) {
                g2d.drawRect(lx + cellSide * j, ly + cellSide * i, cellSide, cellSide);

                // g2d.drawString(area.area[i][j], lx + cellSide * j, ly + cellSide * i + 10);
            }
        for (BoardObject obj : area.getObjects()) {
            if (!obj.isActive()) continue;
            GraphicsBoardObjectDrawer some = (GraphicsBoardObjectDrawer) obj.getObserver();
            if (some != null) some.doDrawing(g2d, lx, ly, cellSide);
        }
        for (Bug bug : area.getBugs()) {
            GraphicsBugDrawer draw = (GraphicsBugDrawer) bug.getObserver();
            if (draw != null) draw.doDrawing(g2d, lx, ly, cellSide);
        }

        for (int i = 0; i < buttons.size(); i++)
        {
            Button button = buttons.get(i);
            button.draw(g2d, blx, ly + cellSide * i, cellSide);
        }
    }
}


abstract class GraphicsBoardObjectDrawer extends BoardObjectDrawer {
    public GraphicsBoardObjectDrawer(BoardObject object) {
        super(object);
    }

    abstract public void doDrawing(Graphics2D surface, int cornerX, int cornerY, int cellSide);
}

class GraphicsSimpleObstacleDrawer extends GraphicsBoardObjectDrawer {

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

class GraphicsArrowDrawer extends GraphicsBoardObjectDrawer {
    public GraphicsArrowDrawer(Arrow object) {
        super(object);
    }

    @Override
    public void redraw() {

    }

    @Override
    public void doDrawing(Graphics2D surface, int cornerX, int cornerY, int cellSide) {
        Arrow obs = (Arrow) observable;
        int row = obs.getOccupied()[0].getRow();
        int column = obs.getOccupied()[0].getColumn();
        int cellX = cornerX + column * cellSide;
        int cellY = cornerY + row * cellSide;
        try {
            BufferedImage img = ImageIO.read(new File("right.png"));
            surface.drawImage(rotateImage(img, obs.getDirection()), cellX, cellY, cellSide, cellSide, null);
        } catch (Exception e) {
            System.out.print("not loaded");

        }
    }

    public static BufferedImage rotateImage(BufferedImage img, Direction dir)
    {
        AffineTransform tr = AffineTransform.getRotateInstance(Math.toRadians(90), img.getWidth() / 2, img.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tr, AffineTransformOp.TYPE_BILINEAR);
        int cnt = 0;
        if (dir == Direction.RIGHT) cnt = 0;
        if (dir == Direction.DOWN) cnt = 1;
        if (dir == Direction.LEFT) cnt = 2;
        if (dir == Direction.UP) cnt = 3;
        BufferedImage nimg = img;
        for (int i = 0; i < cnt; i++) nimg = op.filter(nimg, null);
        return nimg;
    }
}


class GraphicsTrapDrawer extends GraphicsBoardObjectDrawer {
    public GraphicsTrapDrawer(Trap object) {
        super(object);
    }

    @Override
    public void redraw() {

    }

    @Override
    public void doDrawing(Graphics2D surface, int cornerX, int cornerY, int cellSide) {
        Trap obs = (Trap) observable;
        int row = obs.getOccupied()[0].getRow();
        int column = obs.getOccupied()[0].getColumn();
        int cellX = cornerX + column * cellSide;
        int cellY = cornerY + row * cellSide;
        try {
            BufferedImage img = ImageIO.read(new File("trap.png"));
            WritableRaster raster = img.getRaster();
            for (int i = 0; i < raster.getHeight(); i++)
                for (int j = 0; j < raster.getWidth(); j++) {
                    int[] px = raster.getPixel(j, i, (int[]) null);
                    px[0] = (int) ((double) BugColor.byNumber(obs.getColor()).getRed() * ((double) px[0] / 255));
                    px[1] = (int) ((double) BugColor.byNumber(obs.getColor()).getGreen() * ((double) px[1] / 255));
                    px[2] = (int) ((double) BugColor.byNumber(obs.getColor()).getBlue() * ((double) px[2] / 255));

                    raster.setPixel(j, i, px);
                }
            surface.drawImage(img, cellX, cellY, cellSide, cellSide, null);
        } catch (Exception e) {
            System.out.print("not loaded");

        }
    }
}


class GraphicsBugDrawer extends BugDrawer {

    public GraphicsBugDrawer(Bug bug) {
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
                for (int j = 0; j < raster.getWidth(); j++) {

                    int[] px = raster.getPixel(j, i, (int[]) null);

                    px[0] = (int) ((double) BugColor.byNumber(observable.getColor()).getRed() * ((double) px[0] / 255));
                    px[1] = (int) ((double) BugColor.byNumber(observable.getColor()).getGreen() * ((double) px[1] / 255));
                    px[2] = (int) ((double) BugColor.byNumber(observable.getColor()).getBlue() * ((double) px[2] / 255));


                    raster.setPixel(j, i, px);
                }
            surface.drawImage(img, cellX, cellY, cellSide, cellSide, null);
        } catch (Exception e) {
            System.out.print("not loaded");

        }
    }
}

class Button {
    private EditorTool tool;
    private GameInterface gameInterface;
    private BufferedImage[] picture;

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    private int state;
    public static int stateCount = 2;

    public Button(EditorTool tool) throws IOException {
        this.tool = tool;
        if (tool != null) {
            if (this.tool.getGameInterface() == null) throw new AssertionError();
            this.gameInterface = this.tool.getGameInterface();
            state = 0;
        }
        picture = new BufferedImage[stateCount];
    }

    public void onClick() {
        this.gameInterface.buttonClick(tool);
        state ^= 1;
    }

    public void addImage(BufferedImage image, int state) {
        picture[state] = image;
    }

    public void draw(Graphics2D surface, int cornerX, int cornerY, int side) {
        if (picture[state] == null) throw new AssertionError();
        surface.drawImage(picture[state], cornerX, cornerY, side, side, null);
    }
}

class MainForm extends JFrame {
    public MainForm(GameInterface area, ArrayList<Button> buttons) {
        super();
        add(new Surface(area, buttons, this));
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
        myBoard.saveToFile("some.xml");
        ArrayList<Button> buttons = new ArrayList<>();
        final GameInterface gameInterface = new GameInterface(myLevel);
        EditorTool del = new DeleteTool(gameInterface);
        EditorTool leftArrow = new ArrowTool(gameInterface,Direction.LEFT);
        EditorTool rightArrow = new ArrowTool(gameInterface,Direction.RIGHT);
        EditorTool upArrow = new ArrowTool(gameInterface,Direction.UP);
        EditorTool downArrow = new ArrowTool(gameInterface,Direction.DOWN);

        gameInterface.addTool(del);
        gameInterface.addTool(rightArrow);
        gameInterface.addTool(leftArrow);
        gameInterface.addTool(downArrow);
        gameInterface.addTool(upArrow);

        buttons.add(new Button(del));
        buttons.get(0).addImage(ImageIO.read(new File("delete0.png")), 0);
        buttons.get(0).addImage(ImageIO.read(new File("delete1.png")), 1);
        buttons.add(new Button(leftArrow));
        buttons.get(1).addImage(GraphicsArrowDrawer.rotateImage(ImageIO.read(new File("right.png")), Direction.LEFT), 0);
        buttons.get(1).addImage(GraphicsArrowDrawer.rotateImage(ImageIO.read(new File("right1.png")), Direction.LEFT), 1);
        buttons.add(new Button(rightArrow));
        buttons.get(2).addImage(GraphicsArrowDrawer.rotateImage(ImageIO.read(new File("right.png")), Direction.RIGHT), 0);
        buttons.get(2).addImage(GraphicsArrowDrawer.rotateImage(ImageIO.read(new File("right1.png")), Direction.RIGHT), 1);
        buttons.add(new Button(upArrow));
        buttons.get(3).addImage(GraphicsArrowDrawer.rotateImage(ImageIO.read(new File("right.png")), Direction.UP), 0);
        buttons.get(3).addImage(GraphicsArrowDrawer.rotateImage(ImageIO.read(new File("right1.png")), Direction.UP), 1);
        buttons.add(new Button(downArrow));
        buttons.get(4).addImage(GraphicsArrowDrawer.rotateImage(ImageIO.read(new File("right.png")), Direction.DOWN), 0);
        buttons.get(4).addImage(GraphicsArrowDrawer.rotateImage(ImageIO.read(new File("right1.png")), Direction.DOWN), 1);

        final MainForm form = new MainForm(gameInterface, buttons);


        buttons.add(new Button(null) {
            @Override
            public void onClick() {
                Thread play = new Thread() {
                    @Override
                    public void run() {
                        int it = 0;
                        while (gameInterface.getLevel().getBoard().runOneRound())
                        {
                            gameInterface.getLevel().getBoard().drawOneRound();
                            form.repaint();
                            System.out.printf("some %d\n", it++);
                            try
                            {
                                Thread.currentThread().sleep(100);
                            } catch (Exception e)
                            {

                            }

                        }
                    }
                };

                gameInterface.setLocked(true);
                play.start();
                gameInterface.setLocked(false);
            }
        });
        buttons.get(5).addImage(ImageIO.read(new File("play.png")), 0);
        buttons.get(5).addImage(ImageIO.read(new File("play1.png")), 1);

        form.setVisible(true);


        for (BoardObject object : myBoard.getObjects()) {
            if (object.getObserver() != null) continue;
            if (object.getType().equals("Arrow")) new GraphicsArrowDrawer((Arrow) object);
            else if (object.getType().equals("Trap")) new GraphicsTrapDrawer((Trap) object);
            else if (object.getType().equals("SimpleObstacle"))
                new GraphicsSimpleObstacleDrawer((SimpleObstacle) object);
        }
        for (Bug bug : myBoard.getBugs()) {
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
