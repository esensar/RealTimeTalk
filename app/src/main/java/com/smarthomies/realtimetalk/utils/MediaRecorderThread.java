package com.smarthomies.realtimetalk.utils;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.smarthomies.realtimetalk.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class MediaRecorderThread extends HandlerThread {
    private static final String TAG = MediaRecorderThread.class.getSimpleName();

    private int frequency;
    private int channelConfiguration;
    private int audioEncoding;

    private static MediaRecorderThread instance;

    public static void initWithContext(Context context) {
        instance = new MediaRecorderThread(context);
    }

    private Handler mHandler;

    public static MediaRecorderThread getInstance() {
        if(instance == null) {
            throw new RuntimeException("Initialize server first with initWithContext");
        }
        return instance;
    }

    private MediaRecorderThread(Context ctx) {
        super(TAG);
        Properties prop = new Properties();
        try {
            InputStream inputStream = ctx.getResources().openRawResource(R.raw.config);
            prop.load(inputStream);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Can't find config");
        } catch (IOException e) {
            Log.d(TAG, "Can't load config");
        }

        frequency = Integer.parseInt(prop.getProperty("frequency"));
        channelConfiguration = Integer.parseInt(prop.getProperty("client_channel"));
        audioEncoding = Integer.parseInt(prop.getProperty("audio_encoding"));
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(getLooper());
    }

    public SocketAudioRecorder startStreamingAudio(String ip, int port) {
        SocketAudioRecorder recorder = new SocketAudioRecorder(ip, port, frequency, channelConfiguration, audioEncoding);
        mHandler.post(recorder);
        return recorder;
    }

}