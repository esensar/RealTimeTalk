package com.smarthomies.realtimetalk;

import android.app.Application;
import android.util.Log;

import com.smarthomies.realtimetalk.utils.MediaRecorderThread;
import com.smarthomies.realtimetalk.utils.MediaStreamServer;
import com.smarthomies.realtimetalk.utils.MediaStreamerThread;
import com.smarthomies.realtimetalk.utils.RTTAppHelper;
import com.smarthomies.realtimetalk.utils.SocketAudioPlayer;
import com.smarthomies.realtimetalk.utils.SocketAudioRecorder;

import java.net.Socket;

import io.realm.Realm;

/**
 * Created by ensar on 31/10/16.
 */
public class RTTApp extends Application implements MediaStreamServer.CallCallbacks {
    public static final String TAG = RTTApp.class.getSimpleName();

    private SocketAudioRecorder socketAudioRecorder;

    @Override
    public void onCreate() {
        super.onCreate();

        RTTAppHelper.getInstance().initWithContext(getApplicationContext());

        Realm.init(this);

        MediaStreamServer.initWithContext(this);

        MediaStreamServer.getInstance().setCallListener(this);
        MediaStreamServer.getInstance().startListeningToPort();

        MediaStreamerThread.initWithContext(this);
        MediaStreamerThread.getInstance().start();

        MediaRecorderThread.initWithContext(this);
        MediaRecorderThread.getInstance().start();
    }

    @Override
    public void onError(int errorCode) {
        Log.d(TAG, "onError: " + errorCode);
    }

    @Override
    public void onConnectionRequested(Socket sourceSocket) {
        Log.d(TAG, "onConnectionRequested: ");
        if(socketAudioRecorder != null) {
            socketAudioRecorder.stop();
        }
        socketAudioRecorder = MediaRecorderThread.getInstance().startStreamingAudio(sourceSocket.getInetAddress().toString().substring(1), 8087);
    }

    @Override
    public void onConnectionFinished() {
        Log.d(TAG, "onConnectionFinished: ");
        if(socketAudioRecorder != null) {
            socketAudioRecorder.stop();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        MediaStreamServer.getInstance().stop();
        MediaStreamerThread.getInstance().interrupt();
        MediaRecorderThread.getInstance().interrupt();
    }
}
