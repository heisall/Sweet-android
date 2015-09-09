
package com.jovision.xiaowei.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONUtil {
    private static final String TAG = "JSONUtil";

    public static String getRequest(String requestUrl) {
        MyLog.v(TAG, "getRequest:E--requestUrl=" + requestUrl);
        StringBuffer sBuffer = new StringBuffer();
        String result = "";
        URL url = null;
        InputStream in = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type",
                    "application/json; charset=UTF-8");
            int responseCode = conn.getResponseCode();
            MyLog.v(TAG, "responseCode=" + responseCode);
            if (responseCode == 400) {

            } else if (responseCode == 200) {
                byte[] buf = new byte[1024];
                in = conn.getInputStream();
                for (int n; (n = in.read(buf)) != -1;) {
                    sBuffer.append(new String(buf, 0, n, "UTF-8"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != conn) {
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        result = sBuffer.toString();
        MyLog.v(TAG, "getRequest:X--result=" + result);
        return result;
    }

}
