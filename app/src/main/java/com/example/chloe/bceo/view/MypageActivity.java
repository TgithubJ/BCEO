package com.example.chloe.bceo.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chloe.bceo.DBLayout.DatabaseConnector;
import com.example.chloe.bceo.DBLayout.Read;
import com.example.chloe.bceo.R;
import com.example.chloe.bceo.fragment.FragmentBottomMenu;
import com.example.chloe.bceo.model.Product;
import com.example.chloe.bceo.model.User;
import com.example.chloe.bceo.util.HTTPGet;
import com.example.chloe.bceo.util.Image64Base;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class MypageActivity extends AppCompatActivity {
    private ImageButton button;
    private MediaRecorder recorder;
    private MediaPlayer mediaPlayer;
    private Bitmap decodedImage;
    DatabaseConnector databaseConnector;

    //private static final String OUTPUT_FILE= "/sdcard/recordoutput.3gpp";
    private static final String OUTPUT_FILE = Environment.getExternalStorageDirectory().getPath() + "/recordoutput.3gpp";
    private User user;

    private ArrayList<Product> my_prodlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        databaseConnector = new DatabaseConnector(this);
        user = (User) getIntent().getSerializableExtra("user");

        my_prodlist = new ArrayList<Product>();
        String json = updateMyProdList();
        jsonParser(json);



        //Get user
        user = FragmentBottomMenu.getUser();
        Log.d("[Mypage]", "User " + user.getUserID() + " received!");

        final GridView gridview = (GridView) findViewById(R.id.gridview_mypage);
        gridview.setAdapter(new gridAdapter(this));
        gridview.setOnItemClickListener(new girdClickListener(MypageActivity.this));


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

//        button.setOnClickListener(
//                new View.OnClickListener() {
//                    public void onClick(View v) {
//                        buttonClicked(v);
//                    }
//                }
//        );

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    beginRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    stopRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        playRecordingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    playRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stpPlayingRecordingBtn.setOnClickListener(new View.OnClickListener() {
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

    private String updateMyProdList() {

        HTTPGet httpGet = new HTTPGet();
        String urlStr = httpGet.buildURL("my_sell_product?user_id=" + user.getUserID());
        String response = httpGet.getResponse(urlStr);
        Log.d("[HTTPGet]", urlStr);
        Log.d("[HTTPGet]", response);
        return response;
    }

    public class gridAdapter extends BaseAdapter {

        private Context mContext;

        public gridAdapter(Context c) {
            // TODO Auto-generated constructor stub
            mContext = c;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return my_prodlist.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return my_prodlist.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            View grid;

            if(convertView==null){
                grid = new View(mContext);
                LayoutInflater inflater = getLayoutInflater();
                grid=inflater.inflate(R.layout.fragment_grid_item, parent, false);
            }else{
                grid = (View)convertView;
            }

            ImageView imageView = (ImageView)grid.findViewById(R.id.imagepart);
            TextView textView = (TextView)grid.findViewById(R.id.textpart);
            TextView tv_Price = (TextView)grid.findViewById(R.id.text_price);

            Product prod_tmp = my_prodlist.get(position);
            int image_id = prod_tmp.getImageId();

            Read databaseReader = new Read();
            Cursor cursor = databaseReader.getOneImage(image_id, databaseConnector);
            cursor.moveToFirst();
            String response = cursor.getString(1);
            Log.d("[HTTPGet]", response);
            Bitmap bm = Image64Base.decodeBase64(response);



//            HTTPGet httpGet = new HTTPGet();
//            String urlStr = httpGet.buildURL("images?id=" + image_id);
//            String response = httpGet.getResponse(urlStr);
//            Log.d("[HTTPGet]", urlStr);
//            Log.d("[HTTPGet]", response);
//
//            Bitmap bm = Image64Base.decodeBase64(response);

            //ImageView
            imageView.setImageBitmap(bm);

            //TextView
            textView.setText(prod_tmp.getpName());
            tv_Price.setText(Float.toString(prod_tmp.getpPrice()));

            return grid;
        }

    }

    public class girdClickListener implements AdapterView.OnItemClickListener {
        Context c;

        public girdClickListener(Context c){
            this.c = c;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Dialog: show which item clicked
            Toast.makeText(c, "Postion: " + position + "\nID: " + id, Toast.LENGTH_SHORT).show();

            //Start product activity
            Intent intent = new Intent(view.getContext(), SellActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("cmd", "update");
            intent.putExtra("prod", my_prodlist.get(position));
            startActivityForResult(intent, 0);
        }
    }

    void jsonParser(String jsonStr){
        String json = "{'abridged_cast':" + jsonStr + "}";
        Log.d("[jsonParser-MyPage]: ", json);

        JSONObject jsonResponse;
        try {
            ArrayList<String> temp = new ArrayList<String>();
            jsonResponse = new JSONObject(json);
            JSONArray products = jsonResponse.getJSONArray("abridged_cast");

            for(int i = 0; i < products.length(); i++){

                JSONObject p = products.getJSONObject(i);
                String category = p.getString("category");

//                if (category.equals("electronics")) {
////                if (filter_category.equals("ALL") || category.equals(filter_category)) {

                int id = Integer.parseInt(p.getString("id"));
                String name = p.getString("name");
                float price = Float.parseFloat(p.getString("price"));
                String description = p.getString("description");
                int waitlist = Integer.parseInt(p.getString("waitlist"));
                int image_id = Integer.parseInt(p.getString("image_id"));
                int group_id = Integer.parseInt(p.getString("group_id"));
                String status = p.getString("status");


                Product prod_tmp = new Product(id, name, price, description, waitlist, image_id, group_id, category, status);
                Log.d("[Product] ", prod_tmp.toString());
                my_prodlist.add(prod_tmp);

//                    Log.d("[Product] ", prod_tmp.toString());

//                }
            }
            Toast.makeText(this, "Json: "+temp, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void beginRecording() throws Exception {
        killMediaRecorder();
        File outFile = new File(OUTPUT_FILE);
        if (outFile.exists()) {
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

    private void stopRecording() throws Exception {
        if (recorder != null) {
            recorder.stop();
        }
    }

    private void killMediaRecorder() {
        if (recorder != null) {
            recorder.release();
        }
    }

    private void killMediaPlayer() {
        if (mediaPlayer != null) {
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

    private void stopPlayingRecording() throws Exception {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        killMediaRecorder();
        killMediaPlayer();
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

//    public static String encodeTobase64(String filePath) {
//        File dir = Environment.getExternalStorageDirectory();
//        File file = new File(dir, filePath);
//        byte[] bytes = new byte[0];
//        InputStream in = null;
//        try {
//            bytes = Files.readAllBytes(filePath);
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String voiceEncoded = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
//        return voiceEncoded;
//    }

}

