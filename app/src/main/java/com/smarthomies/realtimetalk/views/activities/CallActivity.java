package com.smarthomies.realtimetalk.views.activities;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.utils.MediaStreamClient;
import com.smarthomies.realtimetalk.utils.MediaStreamServer;

public class CallActivity extends AppCompatActivity {

    private TextView serverStatus;
    private EditText serverIp;
    private Button nazovi;
    private MediaStreamClient mss;
    private MediaStreamServer msc;
    // DESIGNATE A PORT
    boolean isRecording;
    private static Handler handler = new Handler();
    Thread t=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        nazovi = (Button) findViewById(R.id.nazovi);
        serverIp = (EditText) findViewById(R.id.ipAdress);
        serverStatus=(TextView) findViewById(R.id.labela);
        nazovi.setOnTouchListener(nazoviL);
        isRecording=false;
        msc=new MediaStreamServer(CallActivity.this);
        t=new Thread(msc);
        t.start();
    }


    private View.OnTouchListener nazoviL=new View.OnTouchListener() {

        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            String ip=serverIp.getText().toString();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    msc.stop();
                    mss=new MediaStreamClient(CallActivity.this,ip);
                    break;
                case MotionEvent.ACTION_UP:
                    mss.stop(CallActivity.this);
                    break;
            }
            return false;
        }
    };

    public static void toast(final String msg, final Context ctx){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Context context = ctx;
                CharSequence text =msg;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

}