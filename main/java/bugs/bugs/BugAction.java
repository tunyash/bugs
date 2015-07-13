package bugs;

/**
 * Created by tunyash on 7/11/15.
 */

abstract public class BugAction {

    /**
     *
     * @param board
     * @param bug
     * @return true if action is performed, or false if it should be hold
     */

    abstract public boolean evaluate(Board board, Bug bug);


    /**
     *
     * @param direction to move bug
     * @return BugAction which moves bug to direction if it's possible
     */
    public static BugAction forceToGo(final Direction direction)
    {
        return new BugAction() {
            /**
             *
             * @param board -- current board
             * @param bug -- bug to move
             * @return always returns true
             */
            @Override
            public boolean evaluate(Board board, Bug bug) {
               // System.out.println("Force to go");
                BoardPosition newPos = bug.getCurrentPosition().move(direction);
               // System.out.println(board.isCorrectPosition(newPos));
                if (!board.isCorrectPosition(newPos)) return true;
               // System.out.println(board.isObstacle(newPos));
                if (board.isObstacle(newPos)) return true;
                bug.setCurrentPosition(newPos);
                return true;
            }
        };
    }

    /**
     * Action to put bug to the board
     * @param pos
     * @return true if bug succesfully appeared and false otherwise
     */
    public static BugAction appear(final BoardPosition pos)
    {
        return new BugAction() {
            @Override
            public boolean evaluate(Board board, Bug bug) {
                if (board.isObstacle(pos)) return false;
                bug.setCurrentPosition(pos);
                return true;
            }
        };
    }

    /**
     * kill the bug
     * @return true
     */
    public static BugAction kill()
    {
        return new BugAction() {
            @Override
            public boolean evaluate(Board board, Bug bug) {
                bug.setLifePoints(0);
                return true;
            }
        };
    }

}
