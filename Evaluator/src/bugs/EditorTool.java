package bugs;

/**
 * Created by tunyash on 7/25/15.
 */
abstract public class EditorTool {
    public EditorTool(GameInterface gameInterface)
    {
        this.gameInterface = gameInterface;
    }

    public GameInterface getGameInterface() {
        return gameInterface;
    }

    public void boardClick(int row, int column) {

    }
    protected GameInterface gameInterface;

}
