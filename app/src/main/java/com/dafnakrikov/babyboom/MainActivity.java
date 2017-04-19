package com.dafnakrikov.babyboom;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
        private RecordButton mRecordButton = null;
        private PlayButton mPlayButton = null;

        public MainActivity() {

        }

        @Override
        public void onCreate(Bundle icicle) {
            super.onCreate(icicle);
            setContentView(R.layout.activity_main);

            mRecordButton = (RecordButton)findViewById(R.id.recordButton);
            mPlayButton = (PlayButton)findViewById(R.id.playButton);
        }

        @Override
        public void onPause() {
            super.onPause();
            MediaRecorder recorder = mRecordButton.getRecorder();
            MediaPlayer player = mPlayButton.getPlayer();

            if (recorder != null) {
                recorder.release();
            }

            if (player != null) {
                player.release();
            }
        }
}
