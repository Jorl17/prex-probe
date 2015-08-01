package Util;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Created by jorl17 on 18/07/15.
 */
public class Utils {
    public static void sleepNoInterrupt(int mil) {
        try { Thread.sleep(mil); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public static <T> String join(String sep, ArrayList<T> objs) {
        return Utils.join(sep, objs, T::toString);
    }

    public static <T> String join(String sep, ArrayList<T> objs, Function<T,String> toStringFunc) {
        if (objs == null || objs.isEmpty())
            return "";
        //FIXME This is probably inneficient
        String s = ",";
        s = toStringFunc.apply(objs.get(0));
        for (int i = 1; i < objs.size(); i++)
            s += sep + toStringFunc.apply(objs.get(i));

        return s;
    }
}
