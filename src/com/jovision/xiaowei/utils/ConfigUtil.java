
package com.jovision.xiaowei.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jovision.AppConsts;
import com.jovision.Jni;
import com.jovision.xiaowei.MainApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigUtil {
    private static final String TAG = "ConfigUtil";

    private static String country = "";// App 当前用的国家
    private final static String CHINA_JSON = "{\"country\":\"\u4e2d\u56fd\"}";
    private final static String TaiWan = "台湾";
    private final static String HongKong = "香港";
    private final static String Macau = "澳门";
    private final static String Tunisia = "突尼斯";
    public static int port = 9200;

    /**
     * 获取新浪返回的地域
     * 
     * @return AppConsts.COUNTRY_CHINA：中国大陆 AppConsts.COUNTRY_OTHER：其他
     */
    public static int getSinaRegion() {
        int region;
        String china = "";
        try {
            JSONObject chinaObj = new JSONObject(CHINA_JSON);
            china = chinaObj.getString("country");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String country = getCountry();
        if (country.contains("EChina")) {// 访问新浪接口出错，根据当前语言判断国家
            if (AppConsts.CURRENT_LAN == AppConsts.LANGUAGE_ZH) {
                region = AppConsts.COUNTRY_CHINA;
            } else {
                region = AppConsts.COUNTRY_OTHER;
            }
        } else {
            if (country.contains(china) || country.contains("China")
                    || country.contains("china")) {
                if (country.contains(TaiWan) || country.contains(HongKong)
                        || country.contains(Macau)) {
                    region = AppConsts.COUNTRY_OTHER;
                } else {
                    region = AppConsts.COUNTRY_CHINA;
                }
            } else {
                region = AppConsts.COUNTRY_OTHER;
            }
        }
        MyLog.v(TAG, "getSinaRegion=" + region + ";中国大陆 0 其他地区1");
        return region;
    }

    /**
     * 初始化云视通库
     */
    public static boolean initCloudSDK(Context context) {

        HashMap<String, String> statusMap = ((MainApplication) context
                .getApplicationContext()).getStatusHashMap();

        boolean initRes = false;
        boolean helperState = false;
        boolean broadState = false;

        //初始化sdk
        if (Boolean.parseBoolean(statusMap.get(AppConsts.INIT_CLOUD_SDK))) {
            MyLog.e(TAG, "cloud sdk has already inited");
        } else {
            initRes = Jni.init((MainApplication) context
                    .getApplicationContext(), port, AppConsts.LOG_CLOUD_PATH);
            statusMap.put(AppConsts.INIT_CLOUD_SDK, String.valueOf(initRes));
        }
        // 开小助手
        if (Boolean.parseBoolean(statusMap.get(AppConsts.HELPER_STATE))) {
            MyLog.e(TAG, "helper has already enabled");
        } else {
            helperState = Jni.enableLinkHelper(true, 3, 10);
            statusMap.put(AppConsts.HELPER_STATE, String.valueOf(helperState));
        }
        // 开广播
        if (Boolean.parseBoolean(statusMap.get(AppConsts.BROADCAST_STATE))) {
            MyLog.e(TAG, "broadcast has already enabled");
        } else {
            int res = Jni.searchLanServer(9400, 6666);
            if (1 == res) {
                broadState = true;
            }
            statusMap.put(AppConsts.BROADCAST_STATE, String.valueOf(broadState));
        }

        Jni.enableLog(true);
        Jni.setThumb(320, 90);
        Jni.setStat(true);
        MyLog.v(TAG, "initRes=" + initRes + ";helperState=" + helperState + ";broadState="
                + broadState);
        
        

        return initRes;
    }

    /**
     * 判断网络是否真正可用
     * 
     * @param context
     * @return true：可用 false：不可用
     */
    public static boolean getNetWorkState(Context context) {
        boolean flag = false;
        ConnectivityManager cManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            int type = info.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {

            } else {

            }
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 获取系统语言
     * 
     * @return 1:中文 2:英文
     */
    public static int getLanguage(Context context) {

        String language = context.getResources().getConfiguration().locale
                .getCountry();
        MyLog.v(TAG, "getLanguage=" + language);

        int lan = AppConsts.LANGUAGE_ZH;
        if (language.equalsIgnoreCase("cn")) {// 简体
            lan = AppConsts.LANGUAGE_ZH;
        } else if (language.equalsIgnoreCase("tw")) {// 繁体
            lan = AppConsts.LANGUAGE_ZHTW;
        } else {// 英文
            lan = AppConsts.LANGUAGE_EN;
        }
        return lan;
    }

    /**
     * 获取国家
     * 
     * @return
     */
    public static String getCountry() {
        MyLog.v(TAG, "getCountry:E--country=" + country);
        if ("".equalsIgnoreCase(country) || country.startsWith("EChina")) {// 国家未取到再调用新浪接口
            String requestRes = "China0";
            try {
                requestRes = JSONUtil.getRequest(Url.SINA_URL);
                MyLog.v(TAG, "requestRes=" + requestRes);
                if (null != requestRes && !"".equalsIgnoreCase(requestRes)) {// 字符串不为null且不为空
                    String jsonStr = requestRes.substring(
                            requestRes.indexOf("{"),
                            requestRes.indexOf("}") + 1);
                    MyLog.v(TAG, "jsonStr=" + jsonStr);
                    JSONObject obj = new JSONObject(jsonStr);
                    int res = obj.getInt("ret");
                    if (1 == res) {
                        country = obj.getString("country") + "-"
                                + obj.getString("province") + "-"
                                + obj.getString("city");
                    } else {
                        country = "EChina3";// 有返回，返回的结果明确说错误
                    }
                } else {
                    country = "EChina1";// 有返回，返回的结果不对
                }
            } catch (Exception e) {
                country = "EChina2";// 抛异常，解析出错
                e.printStackTrace();
            }
            String cacheCountry = MySharedPreference.getString("SINA_COUNTRY");
            MyLog.v(TAG, "1-country(新浪返回国家):" + country);
            MyLog.v(TAG, "2-cacheCountry(缓存国家):" + cacheCountry);
            if ("".equalsIgnoreCase(country) || country.startsWith("EChina")) {// 国家获取出错
                if (null != cacheCountry && !"".equalsIgnoreCase(cacheCountry)) {
                    country = cacheCountry;
                    MyLog.v(TAG, "3-返回结果:" + country);
                }
            } else {// 国家获取成功
                if (!country.equalsIgnoreCase(cacheCountry)) {
                    MySharedPreference.putString("SINA_COUNTRY", country);
                    MyLog.v(TAG, "3-返回结果（sina和缓存不同）:" + country);
                } else {
                    MyLog.v(TAG, "3-返回结果（sina和缓存一样）:" + country);
                }
            }
        }

        MyLog.v(TAG, "getCountry---X:county=" + country);
        return country;
    }

    /**
     * 获取设备的云视通组
     * 
     * @param deviceNum
     */
    public static String getGroup(String deviceNum) {

        StringBuffer groupSB = new StringBuffer();
        if (!"".equalsIgnoreCase(deviceNum)) {
            for (int i = 0; i < deviceNum.length(); i++) {
                if (Character.isLetter(deviceNum.charAt(i))) { // 用char包装类中的判断字母的方法判断每一个字符
                    groupSB = groupSB.append(deviceNum.charAt(i));
                }
            }
        }
        return groupSB.toString();
    }

    /**
     * 获取设备的云视通组和号码
     * 
     * @param deviceNum
     */
    public static int getYST(String deviceNum) {
        int yst = 0;

        StringBuffer ystSB = new StringBuffer();
        if (!"".equalsIgnoreCase(deviceNum)) {
            for (int i = 0; i < deviceNum.length(); i++) {
                if (Character.isDigit(deviceNum.charAt(i))) {
                    ystSB = ystSB.append(deviceNum.charAt(i));
                }
            }
        }

        if ("".equalsIgnoreCase(ystSB.toString())) {
            yst = 0;
        } else {
            yst = Integer.parseInt(ystSB.toString());
        }
        return yst;
    }

    /**
     * 2015.6.9 获取简短title
     * 
     * @param title
     * @return
     */
    public static String getShortTile(String title) {
        if (null == title || "".equalsIgnoreCase(title)) {
            return "";
        }

        String shortTitle = "";
        int length = title.length();
        if (isContainChinese(title)) {// 含有中文
            if (length > 12) {
                shortTitle = title.substring(0, 9) + "...";
            } else {
                shortTitle = title;
            }
        } else {
            if (length > 20) {
                shortTitle = title.substring(0, 18) + "...";
            } else {
                shortTitle = title;
            }
        }

        return shortTitle;
    }

    /**
     * 判断是否含有中文
     * 
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
