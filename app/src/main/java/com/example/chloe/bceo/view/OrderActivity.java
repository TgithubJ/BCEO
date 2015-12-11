package com.example.chloe.bceo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chloe.bceo.R;
import com.example.chloe.bceo.model.Product;
import com.example.chloe.bceo.model.User;
import com.example.chloe.bceo.util.HTTPGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    ArrayList<Product> buyList = new ArrayList<Product>();
    ArrayList<Product> sellList = new ArrayList<Product>();

    private String[] buyArray;
    private String[] sellerArray = {"Title: Cookie \n Price: $10 \n Buyer: Woojoo",
            "Title: Cookie \n Price: $2 \n Buyer: Woojoo"};
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        user = (User) getIntent().getSerializableExtra("user");

        // get buyList
        HTTPGet httpGet = new HTTPGet();
        String urlStr = httpGet.buildURL("/my_buy_product?user_id=" + user.getUserID());
        String jsonString = httpGet.getResponse(urlStr);
        Log.d("[Browse Page] -> URL ", urlStr);
        Log.d("[Browse Page] -> Json ", jsonString);
        jsonParser(jsonString, buyList);
        buyArray = listToArray(buyList);

        // get sellList
        urlStr = httpGet.buildURL("/my_sell_product?user_id=" + user.getUserID());
        jsonString = httpGet.getResponse(urlStr);
        Log.d("[Browse Page] -> URL ", urlStr);
        Log.d("[Browse Page] -> Json ", jsonString);
        // jsonParser(jsonString, sellList);
        // buyArray = listToArray(sellList);

        ArrayAdapter adapterBuy = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, buyArray);
        ListView buyListView = (ListView) findViewById(R.id.listView2);
        buyListView.setAdapter(adapterBuy);
        buyListView.setOnItemClickListener(new ItemClickListener1(OrderActivity.this));

        ArrayAdapter adapterSell = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sellerArray);
        ListView sellListView = (ListView) findViewById(R.id.listView3);
        sellListView.setAdapter(adapterSell);
        sellListView.setOnItemClickListener(new ItemClickListener2(OrderActivity.this));

    }

    public class ItemClickListener1 implements AdapterView.OnItemClickListener {
        Context c;

        public ItemClickListener1(Context c){
            this.c = c;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Dialog: show which item clicked
            Toast.makeText(c, "Postion: "+ position + "\nID: " + id, Toast.LENGTH_SHORT).show();

            //Start product activity
            Intent intent = new Intent(view.getContext(), ProductActivity.class);
            intent.putExtra("prod", buyList.get(position));
            startActivityForResult(intent, 0);
        }
    }

    public class ItemClickListener2 implements AdapterView.OnItemClickListener {
        Context c;

        public ItemClickListener2(Context c){
            this.c = c;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Dialog: show which item clicked
            Toast.makeText(c, "Postion: "+ position + "\nID: " + id, Toast.LENGTH_SHORT).show();

            //Start product activity
            Intent intent = new Intent(view.getContext(), ProductActivity.class);
            intent.putExtra("prod", sellList.get(position));
            startActivityForResult(intent, 0);
        }
    }

    public void jsonParser(String jsonStr, ArrayList<Product> pList){
        String json = "{'abridged_cast':" + jsonStr + "}";
        Log.d("[jsonParser]: ", json);

        JSONObject jsonResponse;
        try {
            ArrayList<String> temp = new ArrayList<String>();
            jsonResponse = new JSONObject(json);
            JSONArray products = jsonResponse.getJSONArray("abridged_cast");

            for(int i = 0; i < products.length(); i++){

                JSONObject p = products.getJSONObject(i);
                String category = p.getString("category");
                int id = Integer.parseInt(p.getString("id"));
                String name = p.getString("name");
                float price = Float.parseFloat(p.getString("price"));
                String description = p.getString("description");
                int waitlist = Integer.parseInt(p.getString("waitlist"));
                int image_id = Integer.parseInt(p.getString("image_id"));
                int group_id = Integer.parseInt(p.getString("group_id"));
                String status = p.getString("status");

                Product prod_tmp = new Product(id, name, price, description, waitlist, image_id, group_id, category, status);
                pList.add(prod_tmp);

                Log.d("[Product] ", prod_tmp.toString());
            }
            Toast.makeText(this, "Json: " + temp, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String[] listToArray (ArrayList<Product> productList) {
        String[] sArray = new String[productList.size()];

        for(int i = 0; i < productList.size(); i++) {
            StringBuffer productInfo = new StringBuffer();
            Product p = productList.get(i);
            productInfo.append("Title: ");
            productInfo.append(p.getpName());
            productInfo.append("\n");
            productInfo.append("Price: $");
            productInfo.append(p.getpPrice());
            productInfo.append("\n");
            sArray[i] = productInfo.toString();
        }
        return sArray;
    }
}

