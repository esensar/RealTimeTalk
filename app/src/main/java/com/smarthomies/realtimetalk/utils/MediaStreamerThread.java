package com.smarthomies.realtimetalk.utils;

import android.content.Context;
import android.databinding.repacked.org.antlr.v4.codegen.model.Loop;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.smarthomies.realtimetalk.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class MediaStreamerThread extends HandlerThread {
    private static final String TAG = MediaStreamerThread.class.getSimpleName();

    private int frequency;
    private int channelConfiguration;
    private int audioEncoding;

    private static MediaStreamerThread instance;

    public static void initWithContext(Context context) {
        instance = new MediaStreamerThread(context);
    }

    private Handler mHandler;

    public static MediaStreamerThread getInstance() {
        if(instance == null) {
            throw new RuntimeException("Initialize server first with initWithContext");
        }
        return instance;
    }

    private MediaStreamerThread(Context ctx) {
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
        channelConfiguration = Integer.parseInt(prop.getProperty("server_channel"));
        audioEncoding = Integer.parseInt(prop.getProperty("audio_encoding"));
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(getLooper());
    }

    public void playAudioFromSocket(Socket socket, final CountDownLatch finishedLock) {
        mHandler.post(new SocketAudioPlayer(socket, frequency, channelConfiguration, audioEncoding, finishedLock));
    }

}