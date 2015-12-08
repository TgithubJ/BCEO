package com.example.chloe.bceo.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;

import com.example.chloe.bceo.R;

import java.io.File;


public class MypageActivity extends AppCompatActivity {
    private ImageButton button;
    private MediaPlayer mediaPlayer;
    private MediaRecorder recorder;
    private Bitmap decodedImage;
    //private static final String OUTPUT_FILE= "/sdcard/recordoutput.3gpp";
    private static final String OUTPUT_FILE= Environment.getExternalStorageDirectory().getPath() + "/recordoutput.3gpp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        button = (ImageButton) this.findViewById(R.id.imageButton);
        ImageButton startBtn = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton endBtn = (ImageButton) findViewById(R.id.imageButton5);
        ImageButton playRecordingBtn = (ImageButton) findViewById(R.id.imageButton6);
        ImageButton stpPlayingRecordingBtn = (ImageButton) findViewById(R.id.imageButton7);

/*        DatabaseConnector databaseConnector = new DatabaseConnector(this);
        Read databaseReader = new Read();
        Cursor cursor = databaseReader.getOneProduct(1, databaseConnector);
        cursor.moveToFirst();
        decodedImage = decodeBase64(cursor.getString(5));
        button.setImageBitmap(decodedImage);*/

        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonClicked(v);
                    }
                }
        );

        startBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try {
                    beginRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                try {
                    stopRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        playRecordingBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                try {
                    playRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stpPlayingRecordingBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                try {
                    stopPlayingRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void buttonClicked(View view) {
        // Check values from editTexts
        startActivity(new Intent(this, SellActivity.class));
    }

    private void beginRecording() throws Exception
    {
        killMediaRecorder();
        File outFile = new File(OUTPUT_FILE);
        if(outFile.exists())
        {
            outFile.delete();
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(OUTPUT_FILE);
        recorder.prepare();
        recorder.start();
/*
        File file = new File(Environment.getExternalStorageDirectory() + "/hello-4.wav");
        byte[] org.apache.commons.io.FileUtils.readFileToByteArray(file);
        InputStream in = new java.io.FileInputStream(file);
        byte[] bytes = in.toString();
        String encoded = Base64.encodeToString(bytes, 0);
*/

    }

    private void stopRecording() throws Exception
    {
        if (recorder != null)
        {
            recorder.stop();
        }
    }

    private void killMediaRecorder()
    {
        if (recorder != null)
        {
            recorder.release();
        }
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

    private void playRecording() throws Exception {
        killMediaPlayer();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(OUTPUT_FILE);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    private void stopPlayingRecording() throws Exception
    {
        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        killMediaRecorder();
        killMediaPlayer();
    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}



