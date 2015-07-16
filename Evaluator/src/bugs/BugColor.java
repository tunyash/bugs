package bugs;

import java.awt.*;

/**
 * Created by tunyash on 7/16/15.
 */
public class BugColor {

    public static Color byNumber(int number)
    {
        if (number == 0) return new Color(24, 107, 106);
        if (number == 1) return new Color(160, 184, 74);
        if (number == 2) return new Color(137, 33, 105);
        if (number == 3) return new Color(255, 107, 78);
        return null;
    }



}
