package com.smarthomies.realtimetalk.utils;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.smarthomies.realtimetalk.views.activities.CallActivity;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class SocketAudioRecorder implements Runnable {
    private static final String TAG = SocketAudioRecorder.class.getSimpleName();

    private static int recBufSize;
    private static AudioRecord audioRecord;

    private boolean isRecording;

    private Socket socket;

    private String ip;
    private int port;

    public SocketAudioRecorder(String ip, int port, int frequency, int channelConfiguration, int audioEncoding) {
        this.ip = ip;
        this.port = port;
        if(audioRecord == null || audioRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
            recBufSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
            Log.d(TAG, "MediaStreamClient buffer size: " + recBufSize);
            //Log.v(TAG,String.valueOf(AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_CONFIGURATION_MONO , AudioFormat.ENCODING_PCM_16BIT)));
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency, channelConfiguration, audioEncoding, recBufSize);
        }
    }

    @Override
    public void run() {
        byte[] buffer = new byte[recBufSize];
        try {
            socket = new Socket(ip, port);
            Log.d(TAG, "Socket open!");
            Log.d(TAG, "Socket info: " + socket);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        audioRecord.startRecording();

        Log.d(TAG, "Started recording");
        isRecording = true;
        while (isRecording) {

            int readSize = audioRecord.read(buffer, 0, recBufSize);

            Log.d(TAG, "Recorded " + readSize + " bytes");

            try {
                socket.getOutputStream().write(buffer, 0, readSize);
                Log.d(TAG, "Sent " + readSize + " bytes");
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        audioRecord.stop();
        Log.d(TAG, "Stopped recording!");
        try {
            socket.close();
            Log.d(TAG, "Socket closed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isRecording = false;
    }

}