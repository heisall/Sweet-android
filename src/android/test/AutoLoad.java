
package android.test;

import com.jovision.xiaowei.utils.MyLog;

public class AutoLoad {

    private static final String TAG = "AutoLoad";

    public static boolean foo() {
        boolean result = false;
        try {
            System.loadLibrary("gnustl_shared");
            System.loadLibrary("stlport_shared");
            System.loadLibrary("tools");
            System.loadLibrary("alu");
            System.loadLibrary("nplayer");
            System.loadLibrary("play");
            result = true;
            MyLog.e(TAG, "AutoLoad libs have done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void push() {
        try {
            System.loadLibrary("jvpush");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
