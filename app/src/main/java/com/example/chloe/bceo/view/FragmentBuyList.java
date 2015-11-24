package com.example.chloe.bceo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chloe.bceo.R;

/**
 * Created by chuntaejin on 11/11/15.
 */
public class FragmentBuyList extends Fragment {
    private String[] musicArray = {"Title: Toaster \n Price: $12 \n Seller: Taejin",
            "Title: Cookie \n Price: $2 \n Seller: Woojoo"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_item,container, false);

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, musicArray);

        ListView musicListView = (ListView) view.findViewById(R.id.listView);
        musicListView.setAdapter(adapter);

        musicListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (position == 0) {
                            startActivity(new Intent(getActivity(), ProductActivity.class));
                        } else if(position == 1) {
                            startActivity(new Intent(getActivity(), ProductActivity.class));
                        }
                    }
                }
        );

        return view;
    }
}
