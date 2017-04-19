package com.dafnakrikov.babyboom;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by yonif on 15/10/2015.
 */
public class PlayButton extends Button {
    boolean mStartPlaying = true;
    private MediaPlayer mPlayer = null;
    private static final String LOG_TAG = "PlayButton";

    OnClickListener clicker = new OnClickListener() {
        public void onClick(View v) {
            onPlay(mStartPlaying);
            if (mStartPlaying) {
                setText("STOP");
            } else {
                setText("PLAY");
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
        setText("PLAY");
        setOnClickListener(clicker);
    }

    public PlayButton(Context context, AttributeSet attrs) {
        super(context, attrs); // This should be first line of constructor
        setText("PLAY");
        setOnClickListener(clicker);
    }

    public PlayButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setText("PLAY");
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
                setText("PLAY");
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
