package com.ldm.http.http;

import java.io.IOException;

/**
 * Created by jxn on 2016/11/27 0027.
 */

public interface IHttpHandler {

    boolean accept(String uri);

    void  handle(String uri, HttpContext httpContext) throws IOException;

}
