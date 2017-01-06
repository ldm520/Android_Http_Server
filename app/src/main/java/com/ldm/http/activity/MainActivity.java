package com.ldm.http.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ldm.http.http.AndroidHttpServer;
import com.ldm.http.http.AssetsResourceHandler;
import com.ldm.http.utils.Constant;
import com.ldm.http.utils.HttpUtils;
import com.ldm.http.R;
import com.ldm.http.http.UploadFileHandler;


/**
 * @author ldm
 * @description 服务端测试
 * @time 2016/12/29 15:20
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private Button start_btn;
    private ImageView show_iv;
    private AndroidHttpServer mServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        start_btn = (Button) findViewById(R.id.start_btn);
        show_iv = (ImageView) findViewById(R.id.show_iv);
        start_btn.setOnClickListener(this);
        //指定服务器端口号为8888
        mServer = new AndroidHttpServer();
        mServer.registerResourceHandler(new AssetsResourceHandler(this));
        mServer.registerResourceHandler(new UploadFileHandler(this) {
            @Override
            protected void onImageLoaded(String path) {
                super.onImageLoaded(path);
            }
        });
    }

    /**
     * @description 显示图片
     * @author ldm
     * @time 2017/1/4 10:05
     */
    private void showImage(final String tmpPath) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeFile(tmpPath);
                show_iv.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start_btn) {
            String ip = HttpUtils.getPhoneIp(this);
            //在浏览器中要通过获取到的ip和端口来访问我们搭建的服务器
            Log.e(Constant.LOG_TAG, "当前服务器Ip：" + ip + "----端口：" + Constant.HTTP_PORT);
            //开户手机端Http服务
            mServer.openServer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止服务
        mServer.closeServer();
    }
}
