package com.example.chloe.bceo.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chloe.bceo.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class BrowseActivityFragment extends Fragment {

    public BrowseActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_browse, container, false);
    }
}
