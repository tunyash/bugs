package bugs.bugs;

public class Main {

    public static void main(String[] args)  throws Exception{
	// write your code here
        Board myBoard = new Board(5, 6);
        myBoard.addObject(new SimpleObstacle(new BoardPosition(0,1)));
        myBoard.addObject(new SimpleObstacle(new BoardPosition(1,3)));
        myBoard.addObject(new SimpleObstacle(new BoardPosition(2,4)));
        myBoard.addObject(new Arrow(new BoardPosition(0,0), Direction.DOWN));
        myBoard.addObject(new Arrow(new BoardPosition(1,0), Direction.RIGHT));
        myBoard.addObject(new Arrow(new BoardPosition(1,1), Direction.RIGHT));
        myBoard.addObject(new Arrow(new BoardPosition(1,2), Direction.UP));
        myBoard.addObject(new Arrow(new BoardPosition(0,2), Direction.RIGHT));
        myBoard.addObject(new Arrow(new BoardPosition(0,3), Direction.LEFT));
        myBoard.addObject(new Arrow(new BoardPosition(2,0), Direction.RIGHT));
        myBoard.addObject(new Arrow(new BoardPosition(2,1), Direction.RIGHT));
        myBoard.addObject(new Trap(new BoardPosition(2,2), 2));

        myBoard.addObject(new Trap(new BoardPosition(0,4), 1));
        myBoard.addBug(new BoardPosition(0, 0), 1);
        myBoard.addBug(new BoardPosition(2, 0), 2);
        System.out.print(myBoard);
        myBoard.runGame();
        System.out.print(myBoard);
        System.out.printf("Score: %d\n", myBoard.getScore());
    }
}
