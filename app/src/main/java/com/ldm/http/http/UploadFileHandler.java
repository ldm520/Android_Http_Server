package com.ldm.http.http;

import android.content.Context;
import android.os.Environment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author ldm
 * @description 上传文件处理，目前只测试了上传图片 比如测试地址：http://192.168.1.114:8123/ldm_image/
 * @time 2017/1/6 16:31
 */

public class UploadFileHandler implements IHttpHandler {


    private String mFilePrefix = "/ldm_image/";

    private Context mContext;

    public UploadFileHandler(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean accept(String uri) {
        //以prefix结尾时返回true
        return uri.startsWith(mFilePrefix);
    }

    @Override
    public void handle(String uri, HttpContext httpContext) throws IOException {
        //上传的图片保存的路径
        String tmpPath = Environment.getExternalStorageDirectory() + "/test_upload.png";
        // 获取到所上传文件的长度，用Postman工具测试，要在工具中手动添加Content-Length
        //我测试用过360浏览器，发现也不能获取到Content-Length，用谷歌浏览器是可以的
        Long totalLength = Long.valueOf(httpContext.getRequestHeaderValue("Content-Length"));
        //输出(保存)到指定文件
        FileOutputStream fos = new FileOutputStream(tmpPath);
        //获取输入流
        InputStream in = httpContext.getSocket().getInputStream();
        byte[] buffer = new byte[10240];
        //从文件流读到buffer中实际的字节长度
        int len = 0;
        // 还未读取的字节数
        long nLeftLength = totalLength;
        while ((len = in.read(buffer)) > 0 && nLeftLength > 0) {
            fos.write(buffer, 0, len);
            nLeftLength -= len;
        }
        fos.close();
        //响应结果
        OutputStream os = httpContext.getSocket().getOutputStream();
        PrintStream printStream = new PrintStream(os);
        printStream.println("HTTP/1.1 200 OK");
        printStream.println();
        printStream.println("Received File");
        printStream.close();
        //处理响应结果
        onImageLoaded(tmpPath);
    }

    /**
     * @description 处理响应结果
     * @author ldm
     * @time 2017/1/6 16:35
     */
    protected void onImageLoaded(String path) {

    }
}
