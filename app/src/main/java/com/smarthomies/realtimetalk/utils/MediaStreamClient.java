package com.smarthomies.realtimetalk.utils;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.views.activities.CallActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

public class MediaStreamClient {

    boolean isRecording;
    int recBufSize;
    //ServerSocket sockfd;
    Socket connfd;
    AudioRecord audioRecord;
    private static final String TAG = "MyActivity";

    public MediaStreamClient(final Context ctx, final String ip) {

        Properties prop = new Properties();
        try {
            InputStream inputStream = ctx.getResources().openRawResource(R.raw.config);
            prop.load(inputStream);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find config");
        } catch (IOException e) {
            System.out.println("Can't load config");
        }

        final int frequency = Integer.parseInt(prop.getProperty("frequency"));
        final int channelConfiguration = Integer.parseInt(prop.getProperty("client_channel"));
        final int audioEncoding = Integer.parseInt(prop.getProperty("audio_encoding"));
        final int SERVERPORT = Integer.parseInt(prop.getProperty("port_server"));


        recBufSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
        Log.d(TAG, "MediaStreamClient buffer size: " + recBufSize);
        //Log.v(TAG,String.valueOf(AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_CONFIGURATION_MONO , AudioFormat.ENCODING_PCM_16BIT)));
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency, channelConfiguration, audioEncoding, recBufSize);

        new Thread() {
            byte[] buffer = new byte[recBufSize];

            public void run() {
                try { connfd = new Socket(ip, SERVERPORT); }
                catch (Exception e) {
                    e.printStackTrace();
                    CallActivity.toast("Can't connect!",ctx);
                    return;
                }
                audioRecord.startRecording();
                isRecording = true;
                while (isRecording) {

                    int readSize = audioRecord.read(buffer, 0, recBufSize);

                    try { connfd.getOutputStream().write(buffer, 0, readSize);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        CallActivity.toast("Closed stream by revicer!",ctx);
                        break;
                    }
                }
                audioRecord.stop();
                audioRecord.release();
                try { connfd.close(); }
                catch (Exception e) {
                    e.printStackTrace();
                    CallActivity.toast("Can't close connection!",ctx);
                }
            }

        }.start();
    }



    public void stop(Context ctx) {
        isRecording = false;
        /*try { connfd.close(); }
        catch (Exception e) {
            e.printStackTrace();
            MainActivity.toast("Can't close connection!",ctx);
        }*/
    }
}