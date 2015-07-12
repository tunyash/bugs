package bugs;

/**
 * Created by tunyash on 7/12/15.
 */
public class ColoredString {
    public static final String[] color =
            {(char) 27 + "[0;37m", (char) 27 + "[0;31m", (char) 27 + "[0;32m", (char) 27 + "[0;34m", (char) 27 + "[0;35m", (char) 27 + "[0;36m"};
    public static String getColored(int color, String s)
    {
        return ColoredString.color[color] + s + (char)27 + "[0m";
    }
}
