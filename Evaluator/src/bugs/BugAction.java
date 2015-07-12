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
                BoardPosition newPos = bug.getCurrentPosition().move(direction);
                if (!board.isCorrectPosition(newPos)) return true;
                if (board.isObstacle(newPos)) return true;
                bug.setCurrentPosition(newPos);
                return true;
            }
        };
    }

}
