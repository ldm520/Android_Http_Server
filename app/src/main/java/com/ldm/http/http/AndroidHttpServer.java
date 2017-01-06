package com.ldm.http.http;

import android.util.Log;

import com.ldm.http.utils.Constant;
import com.ldm.http.utils.HttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ldm
 * @description Android端简易服务器
 * @time 2017/1/6 16:53
 */

public class AndroidHttpServer {

    private final ExecutorService mTreadPool;

    private boolean mIsEnable = false;
    private ServerSocket mSocket;
    private Set<IHttpHandler> mResourceHandlers;

    public AndroidHttpServer() {
        this.mTreadPool = Executors.newCachedThreadPool();
        this.mResourceHandlers = new HashSet<IHttpHandler>();
    }

    /**
     * @description 启动服务器
     * @author ldm
     * @time 2017/1/6 16:15
     */
    public void openServer() {
        mIsEnable = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doProcessASync();
            }
        }).start();
    }

    /**
     * @description 停止服务器
     * @author ldm
     * @time 2017/1/6 16:15
     */
    public void closeServer() {
        if (!mIsEnable)
            return;
        mIsEnable = false;
        try {
            mSocket.close();
            mSocket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doProcessASync() {
        try {
            InetSocketAddress socketAddr = new InetSocketAddress(Constant.HTTP_PORT);
            mSocket = new ServerSocket();
            mSocket.bind(socketAddr);
            while (mIsEnable) {
                // accept是一个阻塞的方法，当有连接时才会返回Socket值
                final Socket remotePeer = mSocket.accept();
                // 每当有一个接入时就放到线程池中处理
                mTreadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        doAfterConnected(remotePeer);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerResourceHandler(IHttpHandler handler) {
        mResourceHandlers.add(handler);
    }

    /**
     * 处理连接后的操作
     */
    private void doAfterConnected(Socket socket) {
        try {
            HttpContext httpContext = new HttpContext();
            httpContext.setSocket(socket);
            InputStream in = socket.getInputStream();
            String headerLine = null;
            String resourceUri = HttpUtils.readHeadLine(in).split(" ")[1];
            while ((headerLine = HttpUtils.readHeadLine(in)) != null) {
                // 头数据会以两个\r\n结尾
                if (headerLine.equals("\r\n"))
                    break;
                Log.e(Constant.LOG_TAG, "headers is :" + headerLine);
                String[] pair = headerLine.split(": ");
                httpContext.addRequestHeader(pair[0], pair[1]);
            }

            for (IHttpHandler handler : mResourceHandlers) {
                if (!handler.accept(resourceUri))
                    continue;
                handler.handle(resourceUri, httpContext);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
