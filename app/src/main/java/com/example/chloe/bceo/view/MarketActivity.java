package com.example.chloe.bceo.view;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chloe.bceo.R;


public class MarketActivity extends AppCompatActivity {
    private static final String OUTPUT_FILE= Environment.getExternalStorageDirectory().getPath() + "/recordoutput.3gpp";
    private MediaPlayer mediaPlayer;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        try {
            playRecording();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent myIntent = new Intent(view.getContext(), ProductActivity.class);
            startActivityForResult(myIntent, 0);
//            UserSubmissionLog userSubmissionLogs= new UserSubmissionLog(position);
//            System.out.println("Position "+position);
        }
    }

    private void playRecording() throws Exception {
        killMediaPlayer();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(OUTPUT_FILE);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    private void killMediaPlayer()
    {
        if (mediaPlayer != null)
        {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
