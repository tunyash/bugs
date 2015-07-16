package bugs;

/**
 * Created by tunyash on 7/16/15.
 */
abstract public class BugDrawer {
    public BugDrawer(Bug bug)
    {
        this.observable = bug;
        bug.setObserver(this);
    }

    abstract public void redraw();

    public Bug getObservable() {
        return observable;
    }

    protected Bug observable;
}
