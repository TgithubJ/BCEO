package com.example.chloe.bceo.view;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.chloe.bceo.R;


/**
 * Created by HsiangLin on 11/12/15.
 */
public class FragmentBottomMenu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_menu, container, false);

        //Button Menu
        Button buttonBrowse = (Button) view.findViewById(R.id.button_browse);
        //Action taken when a button is pressed
        buttonBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), BrowseActivity.class);
                startActivity(myIntent);
            }
        });

        Button buttonOrder = (Button) view.findViewById(R.id.button_order);
        //Action taken when a button is pressed
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), OrderActivity.class);
                startActivity(myIntent);
            }
        });

        Button buttonSell = (Button) view.findViewById(R.id.button_sell);
        //Action taken when a button is pressed
        buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), SellActivity.class);
                startActivity(myIntent);
            }
        });

        Button buttonGroup = (Button) view.findViewById(R.id.button_group);
        //Action taken when a button is pressed
        buttonGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), GroupsActivity.class);
                startActivity(myIntent);
            }
        });

        Button buttonMyPage = (Button) view.findViewById(R.id.button_mypage);
        //Action taken when a button is pressed
        buttonMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MypageActivity.class);
                startActivity(myIntent);
            }
        });

        return view;
    }

}
