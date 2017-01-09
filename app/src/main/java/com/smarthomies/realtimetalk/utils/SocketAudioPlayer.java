package com.smarthomies.realtimetalk.utils;

import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class SocketAudioPlayer implements Runnable {
    private static final String TAG = SocketAudioPlayer.class.getSimpleName();

    private int playBufSize;
    private AudioTrack audioTrack;

    private boolean isPlaying;

    private Socket socket;

    private CountDownLatch completionLock;

    private int noDataCounter = 0;


    public SocketAudioPlayer(Socket socket, int frequency, int channelConfiguration, int audioEncoding, final CountDownLatch countDownLatch) {
        this.socket = socket;
        playBufSize=AudioTrack.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
        audioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL, frequency, channelConfiguration, audioEncoding, playBufSize, AudioTrack.MODE_STREAM);
        audioTrack.setStereoVolume(1f, 1f);
        this.completionLock = countDownLatch;
    }

    @Override
    public void run() {
        try {
            Log.d(TAG, "Start!");
            byte[] buffer = new byte[playBufSize];
            audioTrack.play();
            isPlaying = true;
            while (isPlaying) {
                int readSize = 0;
                try {
                    readSize = socket.getInputStream().read(buffer);

                    Log.d(TAG, "Received " + readSize + " bytes");

                    if(readSize <= 0) {
                        noDataCounter++;
                    } else {
                        noDataCounter = 0;
                    }

                    if(noDataCounter == 10) {
                        break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    isPlaying = false;
                    break;
                }
                audioTrack.write(buffer, 0, readSize);
            }
            audioTrack.stop();
            audioTrack.flush();
        } finally {
            Log.d(TAG, "Done!");
            completionLock.countDown();
        }
    }

}