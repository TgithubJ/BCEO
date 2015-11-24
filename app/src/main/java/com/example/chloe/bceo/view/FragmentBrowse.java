package com.example.chloe.bceo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.chloe.bceo.R;

/**
 * Created by HsiangLin on 11/12/15.
 */
public class FragmentBrowse extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_browse, container, false);

        return view;
    }

}
