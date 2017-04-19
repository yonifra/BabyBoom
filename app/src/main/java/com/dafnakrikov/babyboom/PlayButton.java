package com.dafnakrikov.babyboom;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class PlayButton extends android.support.v7.widget.AppCompatButton {
    boolean mStartPlaying = true;
    private MediaPlayer mPlayer = null;
    private static final String LOG_TAG = "PlayButton";

    OnClickListener clicker = new OnClickListener() {
        public void onClick(View v) {
            onPlay(mStartPlaying);
            if (mStartPlaying) {
                setText(R.string.Stop);
            } else {
                setText(R.string.Play);
            }
            mStartPlaying = !mStartPlaying;
        }
    };

    public MediaPlayer getPlayer()
    {
        return mPlayer;
    }

    public PlayButton(Context ctx) {
        super(ctx);
        initializeButton();
    }

    public PlayButton(Context context, AttributeSet attrs) {
        super(context, attrs); // This should be first line of constructor
        initializeButton();
    }

    public PlayButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeButton();
    }

    private void initializeButton(){
        setText(R.string.Play);
        setOnClickListener(clicker);
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            String mFileName = RecordButton.Filename;

            if(mFileName == null){
                setText(R.string.Play);
                mStartPlaying = false;
                return;
            }

            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            //mPlayer.setLooping(true);
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
