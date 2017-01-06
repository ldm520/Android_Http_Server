package com.ldm.http.http;

import android.content.Context;

import com.ldm.http.utils.HttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author ldm
 * @description 放置在Assets目录下html等文件测试，如测试用的淘宝手机端html文件
 * 文件获取方式：网页端打开https://m.taobao.com/#index,Windows下Ctrl+s键保存即可，把保存的文件复制到assets下
 * 比如在网页端测试：http://192.168.1.114:8123/static/taobao.html
 * @time 2017/1/6 16:38
 */
public class AssetsResourceHandler implements IHttpHandler {

    private String mAcceptPrefix = "/static/";

    private Context mContext;

    public AssetsResourceHandler(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean accept(String uri) {
        return uri.startsWith(mAcceptPrefix);//以prefix结尾时返回true
    }

    @Override
    public void handle(String uri, HttpContext httpContext) throws IOException {
        int startIndex = mAcceptPrefix.length();
        String assetsPath = uri.substring(startIndex);
        //获取Assets下的文件流
        InputStream in = mContext.getAssets().open(assetsPath);
        //读取
        byte[] raw = HttpUtils.readRawFromStream(in);
        in.close();
        OutputStream os = httpContext.getSocket().getOutputStream();
        PrintStream printStream = new PrintStream(os);
        printStream.println("HTTP/1.1 200 OK");
        printStream.println("Content-Length:" + raw.length);
        if (assetsPath.endsWith(".html")) {
            printStream.println("Content-Type:text/html");
        } else if (assetsPath.endsWith(".js")) {
            printStream.println("Content-Type:text/js");
        } else if (assetsPath.endsWith(".css")) {
            printStream.println("Content-Type:text/css");
        } else if (assetsPath.endsWith(".jpg")) {
            printStream.println("Content-Type:text/jpg");
        } else if (assetsPath.endsWith(".png")) {
            printStream.println("Content-Type:text/png");
        }
        printStream.println();
        printStream.write(raw);
    }
}
