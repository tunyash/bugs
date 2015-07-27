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
        System.out.println("board click");
        for (BoardObject obj:gameInterface.getLevel().getBoard().getCellObjects(row,column))
        {
            System.out.printf("Deleted %s\n",obj.getType());
            obj.setActive(false);
        }
    }
}
