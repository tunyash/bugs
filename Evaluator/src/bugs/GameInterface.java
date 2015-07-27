package bugs;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by tunyash on 7/25/15.
 */
public class GameInterface {

    public GameInterface(Level level)
    {
        this.level = level;
        tools = new ArrayList<>();
    }
    public Level getLevel() {
        return level;
    }
    public void addTool(EditorTool tool)
    {
        tools.add(tool);
    }
    public void boardClick(int row, int column)
    {
        if (locked) return;
        System.out.println(activeTool);
        if (activeTool != null) activeTool.boardClick(row, column);
        level.getBoard().drawOneRound();
    }


    public void buttonClick(EditorTool tool)
    {
        if (locked) return;
        System.out.println("button clicked");
        if (activeTool == tool) {
            activeTool = null;
            return;
        }
        for (EditorTool possibleTool: tools)
            if (possibleTool == tool)
            {
                activeTool = tool;
                return;
            }
        throw new AssertionError();
    }

    public boolean isLocked() {
        return locked;
    }
//TODO: saving to file is piece of shit, it should be saved to memory
    public void setLocked(boolean locked) throws Exception{
        if (locked)
        {
            level.getBoard().saveToFile("tmp.xml");
        }
        else
        {
            level.setBoard(Board.loadFromFile(new File("tmp.xml")));
        }
        this.locked = locked;
    }



    private Level level;


    private boolean locked;
    private ArrayList<EditorTool> tools;
    private EditorTool activeTool;

}
