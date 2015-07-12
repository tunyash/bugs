package bugs;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Board myBoard = new Board(5, 6);
        myBoard.addObject(new SimpleObstacle(new BoardPosition(0,1)));
        myBoard.addObject(new SimpleObstacle(new BoardPosition(1,3)));
        myBoard.addObject(new SimpleObstacle(new BoardPosition(2,4)));
        myBoard.addObject(new Arrow(new BoardPosition(0,0), Direction.DOWN));
        myBoard.addBug(new BoardPosition(0, 0), 1);
        System.out.print(myBoard);
        myBoard.runGame();
        System.out.print(myBoard);

    }
}
