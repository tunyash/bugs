package bugs;

/**
 * Created by tunyash on 7/11/15.
 */

abstract public class BugAction implements Comparable<BugAction> {
     public BugAction(int priority)
     {
         this.priority = priority;
     }
    /**
     *
     * @param board
     * @param bug
     * @return true if action is performed, or false if it should be hold
     */

    abstract public boolean evaluate(Board board, Bug bug);

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    protected int priority;
    public int compareTo(BugAction bugAction) {
        if (this.priority < bugAction.priority) return 1;
        if (this.priority > bugAction.priority) return -1;
        return 0;
    }
    /**
     *
     * @param direction to move bug
     * @return BugAction which moves bug to direction if it's possible
     */
    public static final int movementPriority = 10;
    public static final int primaryPriority = 0;



    public static BugAction forceToGo(final Direction direction)
    {
        return new BugAction(BugAction.movementPriority) {

            /**
             *
             * @param board -- current board
             * @param bug -- bug to move
             * @return always returns true
             */

            @Override
            public boolean evaluate(Board board, Bug bug) {
                bug.setMovementActive(true);
                bug.setMovementDirection(direction);
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
        return new BugAction(BugAction.primaryPriority) {
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
        return new BugAction(BugAction.primaryPriority) {
            @Override
            public boolean evaluate(Board board, Bug bug) {
                bug.setLifePoints(0);
                return true;
            }
        };
    }

}
