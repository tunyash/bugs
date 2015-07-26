package bugs;

/**
 * Created by tunyash on 7/26/15.
 */
public class DeleteTool extends EditorTool{

    public DeleteTool(GameInterface gameInterface) {
        super(gameInterface);
    }

    @Override
    public void boardClick(int row, int column) {
        if (!gameInterface.getLevel().isAvaliable(row, column)) return;
        for (Integer id:gameInterface.getLevel().getBoard().getCellObjects(row,column))
        {
            gameInterface.getLevel().getBoard().getObjectById(id).setActive(false);
        }
    }
}
