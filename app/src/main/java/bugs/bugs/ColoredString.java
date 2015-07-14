package bugs.bugs;

/**
 * Created by tunyash on 7/12/15.
 */
public class ColoredString {
    public static final String[] colors = {"r", "b", "g"};
    public static String getColored(int color, String s) {
        return colors[color] + s;
    }
}
