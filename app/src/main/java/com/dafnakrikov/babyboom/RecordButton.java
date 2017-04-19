package com.dafnakrikov.babyboom;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yonif on 15/10/2015.
 */
public class RecordButton extends android.support.v7.widget.AppCompatButton {
    boolean mStartRecording = true;
    private MediaRecorder mRecorder = null;
    private static final String LOG_TAG = "RecordButton";
    private static String mFileName = null;
    private static final String RECORDINGS_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BabyBoom";

    public MediaRecorder getRecorder()
    {
        return mRecorder;
    }

    OnClickListener clicker = new OnClickListener() {
        public void onClick(View v) {
            onRecord(mStartRecording);
            if (mStartRecording) {
                setText("STOP");
            } else {
                setText("RECORD");
            }
            mStartRecording = !mStartRecording;
        }
    };

    public RecordButton(Context ctx) {
        super(ctx);
        setText("RECORD");
        setOnClickListener(clicker);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs); // This should be first line of constructor
        setText("RECORD");
        setOnClickListener(clicker);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setText("RECORD");
        setOnClickListener(clicker);
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        GenerateNewFileName();
        try {
            mRecorder = new MediaRecorder();

            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(mFileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }

            mRecorder.start();
        }
        catch (RuntimeException ex)
        {
            Log.e(LOG_TAG, "Failed to initialize MediaRecorder");
        }
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        Filename = mFileName;
    }

    public static String Filename;

    private void GenerateNewFileName() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        Date date = new Date();

        File basePath = new File(RECORDINGS_PATH);

        if (!basePath.exists())
        {
            // Create the path
            basePath.mkdir();
        }

        mFileName = RECORDINGS_PATH;
        mFileName += "/" + dateFormat.format(date) + ".3gp";
    }
}
