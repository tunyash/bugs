package bugs;

/**
 * Created by tunyash on 7/26/15.
 */
public class ArrowTool extends EditorTool {

    private final Direction direction;
    public ArrowTool(GameInterface gameInterface, Direction direction) {
        super(gameInterface);
        this.direction = direction;
    }

    @Override
    public void boardClick(int row, int column) {
        if (!gameInterface.getLevel().isObjectAvailable("Arrow")
                || !gameInterface.getLevel().isAvaliable(row,column)) return;
        System.out.println("added arrow");
        gameInterface.getLevel().getBoard().addObject(new Arrow(new BoardPosition(row,column), direction));
        gameInterface.getLevel().decrease("Arrow");
    }
}
