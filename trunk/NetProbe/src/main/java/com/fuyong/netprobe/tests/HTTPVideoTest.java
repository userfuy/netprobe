package com.fuyong.netprobe.tests;

import android.net.TrafficStats;
import android.os.Handler;
import android.os.Process;

import com.fuyong.netprobe.MyApp;
import com.fuyong.netprobe.common.Log;
import com.fuyong.netprobe.common.MediaPlayerListener;
import com.fuyong.netprobe.ui.MainActivity;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: democrazy
 * Date: 13-7-20
 * Time: 下午4:05
 * To change this template use File | Settings | File Templates.
 */
public class HTTPVideoTest extends Test {
    public static final String TAG_URL = "url";
    public static final String TAG_X = "x";
    public static final String TAG_Y = "y";
    public static final String TAG_COUNT = "count";
    public static final String TAG_TEST_INTERVAL = "test-interval";
    public static final long DATA_1MB = 1024 * 1024;
    private List<String> urlList = new ArrayList<>();
    private int x;
    private int y;
    private int interval;
    private int count;
    private Handler handler;
    private MediaPlayerListener mMediaPlayerListener;
    private long mUidRxBytes;

    @Override
    public void config(Element element) {
        if (null == element) {
            return;
        }
        for (Iterator iter = element.elementIterator(TAG_URL); iter.hasNext(); ) {
            Element url = (Element) iter.next();
            urlList.add(getStringValue(url.getTextTrim(), "www.youku.com"));
        }
        x = Integer.parseInt(getStringValue(element.elementTextTrim(TAG_X), "0"));
        y = Integer.parseInt(getStringValue(element.elementTextTrim(TAG_Y), "0"));
        count = Integer.parseInt(getStringValue(element.elementTextTrim(TAG_COUNT), "5"));
        interval = Integer.parseInt(getStringValue(element.elementTextTrim(TAG_TEST_INTERVAL), "5"));
    }

    @Override
    public Object call() {
        Log.info("[HTTPVideoTest] begin http video test");
        boolean ret = true;
        try {
            handler = MyApp.getInstance().getMainActivityHandler();
            if (!initWebView()) {
                return false;
            }
            initMediaPlayerListener();
            handler.sendMessage(handler.obtainMessage(MainActivity.MSG_BEGIN_WEB_VIDEO_TEST));
            for (int i = 0; i < count; ++i) {
                for (String url : urlList) {
                    handler.sendMessage(handler.obtainMessage(MainActivity.MSG_LOAD_URL, url));
                    synchronized (HTTPVideoTest.this) {
                        wait();
                    }
                    handler.sendMessage(handler.obtainMessage(MainActivity.MSG_STOP_LOADING));
                    Thread.sleep(1000 * interval);
                }
            }
            mMediaPlayerListener.stopListener();
        } catch (InterruptedException e) {
            Log.e("HTTPVideoTest", e);
            ret = false;
        } catch (Exception e) {
            Log.e("HTTPVideoTest", e);
            ret = false;
        }
        Log.info("[HTTPVideoTest] end http video test");
        handler.sendMessage(handler.obtainMessage(MainActivity.MSG_END_WEB_VIDEO_TEST));
        return ret;
    }

    private void initMediaPlayerListener() {
        mMediaPlayerListener = new MediaPlayerListener();
        mMediaPlayerListener.setListener(new MediaPlayerListener.Listener() {
            @Override
            public void onSetDataSource(String src) {
                Log.info("[HTTPVideoTest] play " + src);
            }

            @Override
            public void onMediaPlayerStarted() {
                mUidRxBytes = TrafficStats.getUidRxBytes(Process.myUid());
                Log.info("[HTTPVideoTest] media player started");
            }

            @Override
            public void onMediaPaused() {
                Log.info("[HTTPVideoTest] media player paused");
            }

            @Override
            public void onMediaStopped() {
                Log.info("[HTTPVideoTest] media player stopped");
            }

            @Override
            public void onMediaCompleted() {
                long rxBytes = TrafficStats.getUidRxBytes(Process.myUid()) - mUidRxBytes;
                // 判断下载流量大小，过滤广告下载
                if (rxBytes < DATA_1MB) {
                    Log.info("[HTTPVideoTest] video data traffic < 1MB, it is ads");
                    return;
                }
                Log.info("[HTTPVideoTest] media player completed");
                stopWait();
            }

            @Override
            public void onMediaError(int ext1, int ext2) {
                Log.info("[HTTPVideoTest] media player error: " + ext1 + ", " + ext2);
                stopWait();
            }

            @Override
            public void onMediaBufferingUpdate(int process) {
                Log.info("[HTTPVideoTest] media player buffering: " + process);
            }

            @Override
            public void onMediaSkipped() {
                Log.info("[HTTPVideoTest] media player skipped");
            }
        });
        mMediaPlayerListener.startListen();
    }

    private void stopWait() {
        synchronized (HTTPVideoTest.this) {
            notifyAll();
        }
    }

    private boolean initWebView() {
        handler.sendMessage(handler.obtainMessage(MainActivity.MSG_NEW_WEBVIEW));
        int i = 0;
        MyWebView myWebView = null;
        while (null == myWebView && i++ < 10) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.e("HTTPVideoTest", e);
            }
            myWebView = MyApp.getInstance().getWebView();
        }
        if (null == myWebView) {
            return false;
        }
        myWebView.setWebViewListener(new MyWebView.WebViewListener() {
            @Override
            public void onProgressChanged(int newProgress) {

            }

            @Override
            public void onPageStarted(String url) {
                Log.info("[HTTPVideoTest] page started: " + url);
            }

            @Override
            public void onPageFinished(String url) {
                Log.info("[HTTPVideoTest] page finished: " + url);
                handler.sendMessage(handler.obtainMessage(MainActivity.MSG_PLAY_VIDEO, x, y));
            }

            @Override
            public void onReceivedError(int errorCode, String description, String failingUrl) {
                Log.info("[HTTPVideoTest] page error:" + failingUrl + " error code: " + errorCode
                        + " \ndescription: " + description);
                stopWait();
            }
        });
        return true;
    }
}
