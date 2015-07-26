package bugs;

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
        if (activeTool != null) activeTool.boardClick(row, column);
    }
    public void buttonClick(int num)
    {
        if (num < tools.size() && activeTool != null) activeTool = tools.get(num);
    }

    private Level level;
    private ArrayList<EditorTool> tools;
    private EditorTool activeTool;

}
