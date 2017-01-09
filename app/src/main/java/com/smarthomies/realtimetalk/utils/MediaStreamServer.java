package com.smarthomies.realtimetalk.utils;

import android.content.Context;
import android.util.Log;

import com.smarthomies.realtimetalk.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class MediaStreamServer implements Runnable {
    private static final String TAG = MediaStreamServer.class.getSimpleName();

    private boolean isListening;
    private Socket socketConnection;
    private ServerSocket serverSocket;
    private final int SERVERPORT;

    private Thread serverThread;

    private CallCallbacks callListener;

    private static MediaStreamServer instance;

    public static void initWithContext(Context context) {
        instance = new MediaStreamServer(context);
    }

    public static MediaStreamServer getInstance() {
        if(instance == null) {
            throw new RuntimeException("Initialize server first with initWithContext");
        }
        return instance;
    }

    public void setCallListener(CallCallbacks callListener) {
        this.callListener = callListener;
    }

    public void removeCallListener() {
        this.callListener = null;
    }

    private MediaStreamServer(Context ctx) {

        Properties prop = new Properties();
        try {
            InputStream inputStream = ctx.getResources().openRawResource(R.raw.config);
            prop.load(inputStream);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Can't find config");
        } catch (IOException e) {
            Log.d(TAG, "Can't load config");
        }

        SERVERPORT = Integer.parseInt(prop.getProperty("port_client"));
    }

    public void stop() {
        isListening = false;
        serverThread.interrupt();
    }

    public void startListeningToPort() {
        if(serverThread == null) {
            serverThread = new Thread(this);
        }
        serverThread.start();
    }

    public void stopListeningToPort() {
        isListening = false;
    }

    @Override
    public void run() {
        isListening = true;
        try {
            serverSocket = new ServerSocket(SERVERPORT);
            Log.d(TAG, "Server socket open on port: " + SERVERPORT);
        }
        catch (IOException e) {
            e.printStackTrace();
            notifyError(ERROR_PORT_UNAVAILABLE);
            isListening = false;
            return;
        }

        while(isListening) {
            try {
                socketConnection = serverSocket.accept();
                Log.d(TAG, "Accepted socket connection!");
                Log.d(TAG, "Socket connection info: " + socketConnection);
                notifyConnectionRequested(socketConnection);
            } catch (IOException e) {
                e.printStackTrace();
                notifyError(ERROR_WHILE_ACCEPTING);
                continue;
            }

            final CountDownLatch callTerminationLock = new CountDownLatch(1);

            Log.d(TAG, "Playing audio!");
            MediaStreamerThread.getInstance().playAudioFromSocket(socketConnection, callTerminationLock);

            try {
                callTerminationLock.await();
                Log.d(TAG, "Done playing!");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                notifyError(ERROR_CALL_INTERRUPTED);
                continue;
            }

            try {
                socketConnection.close();
                notifyConnectionFinished();
            } catch (Exception e) {
                e.printStackTrace();
                notifyError(ERROR_WHILE_CLOSING_CONNECTION);
            }

        }

        try {
            serverSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            notifyError(ERROR_WHILE_CLOSING);
            isListening = false;
        }

    }

    private void notifyError(int errorCode) {
        if(callListener != null) {
            callListener.onError(errorCode);
        }
    }

    private void notifyConnectionRequested(Socket sourceSocket) {
        if(callListener != null) {
            callListener.onConnectionRequested(sourceSocket);
        }
    }

    private void notifyConnectionFinished() {
        if(callListener != null) {
            callListener.onConnectionFinished();
        }
    }

    public static final int ERROR_PORT_UNAVAILABLE = 0;
    public static final int ERROR_WHILE_CLOSING = 1;
    public static final int ERROR_WHILE_ACCEPTING = 2;
    public static final int ERROR_WHILE_PLAYING = 3;
    public static final int ERROR_WHILE_CLOSING_CONNECTION = 4;
    public static final int ERROR_CALL_INTERRUPTED = 5;


    public interface CallCallbacks {
        void onError(int errorCode);
        void onConnectionRequested(Socket sourceSocket);
        void onConnectionFinished();
    }
}