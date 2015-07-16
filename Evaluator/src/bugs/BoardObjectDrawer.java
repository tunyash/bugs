package bugs;

/**
 * Created by tunyash on 7/16/15.
 */
abstract public class BoardObjectDrawer {

    /**
     *
     * @param object is object for watching
     */
    public BoardObjectDrawer(BoardObject object)
    {
        this.observable = object;
        object.setObserver(this);
    }
    /**
     * notifies that observing BoardObject should be redrawn
     */
    abstract public void redraw();

    public BoardObject getObservable() {
        return observable;
    }

    protected BoardObject observable;
}
