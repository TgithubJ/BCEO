package com.example.chloe.bceo.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


public class BrowseActivity extends AppCompatActivity {

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.androider_01,
            R.drawable.androider_02,
            R.drawable.androider_03,
            R.drawable.androider_04,
            R.drawable.androider_05,
            R.drawable.androider_06,
            R.drawable.androider_07,
            R.drawable.androider_08,
            R.drawable.androider_09,
    };

    public class MyAdapter extends BaseAdapter {

        private Context mContext;

        public MyAdapter(Context c) {
            // TODO Auto-generated constructor stub
            mContext = c;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return mThumbIds[arg0];
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
                LayoutInflater inflater=getLayoutInflater();
                grid=inflater.inflate(R.layout.fragment_grid_item, parent, false);
            }else{
                grid = (View)convertView;
            }

            ImageView imageView = (ImageView)grid.findViewById(R.id.imagepart);
            TextView textView = (TextView)grid.findViewById(R.id.textpart);
            imageView.setImageResource(mThumbIds[position]);
            textView.setText(String.valueOf(position));

            return grid;
        }

    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new MyAdapter(this));
        gridview.setOnItemClickListener(new ItemClickListener());

//        setupBottomMenu();

    }

//    public void setupBottomMenu(){
//        //Button Menu
//        Button buttonBrowse = (Button) findViewById(R.id.button_browse);
//        //Action taken when a button is pressed
//        buttonBrowse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(v.getContext(), BrowseActivity.class);
//                startActivity(myIntent);
//            }
//        });
//
//        Button buttonOrder = (Button) findViewById(R.id.button_order);
//        //Action taken when a button is pressed
//        buttonOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(v.getContext(), OrderActivity.class);
//                startActivity(myIntent);
//            }
//        });
//
//        Button buttonSell = (Button) findViewById(R.id.button_sell);
//        //Action taken when a button is pressed
//        buttonSell.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(v.getContext(), SellActivity.class);
//                startActivity(myIntent);
//            }
//        });
//
//        Button buttonGroup = (Button) findViewById(R.id.button_order);
//        //Action taken when a button is pressed
//        buttonGroup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(v.getContext(), GroupsActivity.class);
//                startActivity(myIntent);
//            }
//        });
//
//        Button buttonMyPage = (Button) findViewById(R.id.button_mypage);
//        //Action taken when a button is pressed
//        buttonMyPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(v.getContext(), MypageActivity.class);
//                startActivity(myIntent);
//            }
//        });
//    }

    public class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent myIntent = new Intent(view.getContext(), ProductActivity.class);
            startActivityForResult(myIntent, 0);
//            UserSubmissionLog userSubmissionLogs= new UserSubmissionLog(position);
//            System.out.println("Position "+position);
        }
    }
}

