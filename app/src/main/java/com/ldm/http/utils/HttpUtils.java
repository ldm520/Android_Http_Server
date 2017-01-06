package com.ldm.http.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author ldm
 * @description 项目中Http工具类
 * @time 2017/1/6 16:44
 */
public class HttpUtils {
    /**
     * 判断网络是否连接
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] infos = cm.getAllNetworkInfo();
        if (infos != null) {
            for (NetworkInfo info : infos) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @description 获取手机ip
     * @author ldm
     * @time 2016/6/21 14:24
     */
    public static String getPhoneIp(Context mContext) {
        String ip = "";
        if (isNetworkConnected(mContext)) {
            try {
                WifiManager wifiManager = (WifiManager) mContext
                        .getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (null != wifiInfo) {
                    int i = wifiInfo.getIpAddress();
                    ip = int2ip(i);
                } else {
                    ip = getGprsIpAddress();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ip;
    }

    /**
     * @description 将ip的整数形式转换成ip形式
     * @author ldm
     * @time 2016/6/21 14:29
     */
    public static String int2ip(int ipInt) {
        return String.valueOf(ipInt & 0xFF) + "." +
                ((ipInt >> 8) & 0xFF) + "." +
                ((ipInt >> 16) & 0xFF) + "." +
                ((ipInt >> 24) & 0xFF);
    }

    /**
     * @description 获取GPRS网络下手机ip
     * @author ldm
     * @time 2016/6/21 14:30
     */
    private static String getGprsIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * @description 读取Http协议Header信息
     * @author ldm
     * @time 2017/1/6 16:42
     */
    public static final String readHeadLine(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c1 = 0;
        int c2 = 0;
        // c2=-1表示遍历到流的结尾
        while (c2 != -1 && !(c1 == '\r' && c2 == '\n')) {
            c1 = c2;
            c2 = in.read();
            sb.append((char) c2);
        }
        if (sb.length() == 0)
            return null;
        if (sb.length() > 2) {
            return sb.toString().substring(0, sb.length() - 2);
        }
        return sb.toString();
    }

    public static byte[] readRawFromStream(InputStream in) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int len = -1;
        while ((len = in.read(buffer)) > 0) {
            bos.write(buffer, 0, len);
        }
        return bos.toByteArray();
    }
}
