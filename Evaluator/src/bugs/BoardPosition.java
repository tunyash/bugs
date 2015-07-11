package bugs;

/**
 * Created by tunyash on 7/11/15.
 * Pair of two ints.
 * @author tunyash
 */
public class BoardPosition {
    public BoardPosition(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    public BoardPosition move(Direction dir)
    {
        return new BoardPosition(row + dir.getDr(), column + dir.getDc());
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }


    private int row, column;

}
