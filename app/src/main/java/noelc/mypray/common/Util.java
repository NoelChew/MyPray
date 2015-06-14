package noelc.mypray.common;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by noelchew on 6/14/15.
 */
public class Util {

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static String doubleToTwoDecimalString(double number) {
        String result = String.valueOf(((double) Math.round(number * 100))/100);
        if (!result.contains(".")) {
            return result + ".00";
        } else {
            String[] tmpArray = result.split("\\.");
            if (tmpArray.length < 1) {
                return result + ".00";
            } else if (tmpArray[1].length() < 1) {
                return tmpArray[0] + ".00";
            } else if (tmpArray[1].length() < 2) {
                return tmpArray[0] + "." + tmpArray[1] + "0";
            } else {
                return tmpArray[0] + "." + tmpArray[1].substring(0, 2);
            }
        }
    }
}
