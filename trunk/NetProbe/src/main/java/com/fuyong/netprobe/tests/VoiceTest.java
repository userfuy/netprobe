package com.fuyong.netprobe.tests;

import android.telephony.TelephonyManager;

import com.fuyong.netprobe.MyScheduledThreadPool;
import com.fuyong.netprobe.PhoneStateReceiver;
import com.fuyong.netprobe.common.Log;
import com.fuyong.netprobe.common.TelephonyUtil;

import org.dom4j.Element;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: democrazy
 * Date: 13-6-23
 * Time: 下午4:45
 * To change this template use File | Settings | File Templates.
 */
public class VoiceTest extends Test implements Observer {

    private String number;
    private int callTime;
    private int count;
    private int interval;

    private ScheduledFuture<?> endCallTask;

    @Override
    public void config(Element element) {
        if (null == element) {
            return;
        }
        number = getStringValue(element.elementTextTrim("number"), "10086");
        callTime = Integer.parseInt(getStringValue(element.elementTextTrim("call-time"), "10"));
        count = Integer.parseInt(getStringValue(element.elementTextTrim("count"), "5"));
        interval = Integer.parseInt(getStringValue(element.elementTextTrim("test-interval"), "5"));
    }

    @Override
    public Object call() {
        try {
            log.info("begin voice test");
            PhoneStateReceiver.getInstance().addObserver(this);
            for (int i = 0; i < count; ++i) {
                log.info("test index: " + i);
                onStartCall();
                if (TelephonyUtil.dial(number)) {
                    synchronized (this) {
                        wait(3000 * callTime);
                    }
                } else {
                    onCallFailed();
                }
                Thread.sleep(1000 * interval);
            }
        } catch (InterruptedException e) {
            Log.e("VoiceTest", e);
            endCall();
        } catch (Exception e) {
            Log.e("VoiceTest", e);
        } finally {
            PhoneStateReceiver.getInstance().deleteObserver(this);
        }
        log.info("end voice test");
        return null;
    }

    private void endCall() {
        TelephonyUtil.endCall();
        stopWait();
    }

    private void stopWait() {
        synchronized (this) {
            notifyAll();
        }
    }

    private void onStartCall() {
    }

    private void onRinging() {
    }

    private void onCallEstablished() {

    }

    private void onEndCall() {
    }

    private void onCallFailed() {

    }

    @Override
    public void update(Observable observable, Object data) {
        Integer state = (Integer) data;
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                if (null != endCallTask) {
                    if (endCallTask.isDone()) {

                    } else {
                        endCallTask.cancel(true);
                    }
                }
                stopWait();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                onCallEstablished();
                endCallTask = MyScheduledThreadPool.getExecutor().schedule(new Runnable() {
                    @Override
                    public void run() {
                        endCall();
                    }
                }
                        , callTime
                        , TimeUnit.SECONDS);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                onRinging();
                break;
        }
    }
}