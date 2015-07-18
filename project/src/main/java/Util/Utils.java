package Util;

/**
 * Created by jorl17 on 18/07/15.
 */
public class Utils {
    public static void sleepNoInterrupt(int mil) {
        try { Thread.sleep(mil); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
