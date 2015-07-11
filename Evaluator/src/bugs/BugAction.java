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

}
