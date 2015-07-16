package bugs;

import javax.swing.*;
import java.awt.*;

class Surface extends JPanel {
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


    }
}


class MainForm extends JFrame
{
    public MainForm()
    {
        super();
        add(new Surface());
        setTitle("Lines");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Area {
    public String[][] area;
    public int height, width;

    public Area(int height, int width) {
        this.height = height;
        this.width = width;
        area = new String[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                area[i][j] = ".";
    }

    public void clear()
    {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                area[i][j] = ".";
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) builder.append(area[i][j]);
            builder.append("\n");
        }
        return builder.toString();
    }
}

class ConsoleBoardObjectDrawer extends BoardObjectDrawer {

    public Area area;

    public ConsoleBoardObjectDrawer(BoardObject object) {
        super(object);
    }
    @Override
    public void redraw() {
        for (BoardPosition pos : observable.getOccupied())
            area.area[pos.getRow()][pos.getColumn()] = observable.toString();
    }
}

class ConsoleBugDrawer extends BugDrawer {

    public Area area;

    public ConsoleBugDrawer(Bug bug) {
        super(bug);
    }
    @Override
    public void redraw() {
        if (observable.getCurrentPosition() != null && observable.getLifePoints() > 0)
            area.area[observable.getCurrentPosition().getRow()][observable.getCurrentPosition().getColumn()] = observable.toString();
    }
}



public class Main {

    public static void main(String[] args) throws Exception {
        // write your code here
        //Runtime.getRuntime().exit(0);
     //   MainForm form = new MainForm();
     //   form.setVisible(true);
        // System.out.println(Runtime.getRuntime().availableProcessors());
        Board myBoard = new Board(5, 6);
        myBoard.addObject(new SimpleObstacle(new BoardPosition(0, 1)));
        myBoard.addObject(new SimpleObstacle(new BoardPosition(1, 3)));
        myBoard.addObject(new SimpleObstacle(new BoardPosition(2, 4)));
        myBoard.addObject(new Arrow(new BoardPosition(0, 0), Direction.DOWN));
        myBoard.addObject(new Arrow(new BoardPosition(1, 0), Direction.RIGHT));
        myBoard.addObject(new Arrow(new BoardPosition(1, 1), Direction.RIGHT));
        myBoard.addObject(new Arrow(new BoardPosition(1, 2), Direction.UP));
        myBoard.addObject(new Arrow(new BoardPosition(0, 2), Direction.RIGHT));
        myBoard.addObject(new Arrow(new BoardPosition(0, 3), Direction.LEFT));
        myBoard.addObject(new Arrow(new BoardPosition(2, 0), Direction.RIGHT));
        myBoard.addObject(new Trap(new BoardPosition(2, 2), 2));

        myBoard.addObject(new Trap(new BoardPosition(0, 4), 1));
        myBoard.addBug(new BoardPosition(0, 0), 1);
        myBoard.addBug(new BoardPosition(2, 0), 2);

        Area area = new Area(myBoard.getHeight(), myBoard.getWidth());
        for (BoardObject object: myBoard.getObjects()) {
            ConsoleBoardObjectDrawer draw0 = new ConsoleBoardObjectDrawer(object);
            draw0.area = area;
        }
        for (Bug bug: myBoard.getBugs()) {
            ConsoleBugDrawer draw0 = new ConsoleBugDrawer(bug);
            draw0.area = area;
        }
        while (myBoard.runOneRound()) {
            area.clear();
            myBoard.drawOneRound();
            System.out.print(area);
        }
        System.out.printf("Score: %d\n", myBoard.getScore());
    }



}
