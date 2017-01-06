package com.ldm.http.http;

import java.net.Socket;
import java.util.HashMap;

/**
 * Created by jxn on 2016/11/27 0027.
 */

public class HttpContext {

    private final HashMap<String, String> requestHeaders;

    public HttpContext() {
        requestHeaders = new HashMap<String, String>();
    }

    private Socket socket;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void addRequestHeader(String headerKey, String headerValue) {
        requestHeaders.put(headerKey, headerValue);
    }

    public String getRequestHeaderValue(String headerKey){
        return requestHeaders.get(headerKey);
    }

}
