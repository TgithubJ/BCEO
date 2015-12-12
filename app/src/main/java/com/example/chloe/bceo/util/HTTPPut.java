package com.example.chloe.bceo.util;

import android.util.Log;

import com.example.chloe.bceo.model.Product;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HsiangLin on 12/10/15.
 */
public class HTTPPut {
    Product p;

    public HTTPPut(Product p){
        this.p = p;
    }

    public void execute(int product_id) {

        URL url = null;
        try {
            url = new URL("http://52.34.169.54:3000//products/" + Integer.toString(product_id));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //--This code works for updating a record from the feed--
        HttpPut httpPut = new HttpPut(url.toString());
        String json = convert2Json();

        StringEntity entity = null;

        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        entity.setContentType("application/json;charset=UTF-8");//text/plain;charset=UTF-8
        entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
        httpPut.setEntity(entity);

        // Send request
        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPut);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity entity1 = response.getEntity();
    }

    String convert2Json(){
        Map<String, String> comment = new HashMap<String, String>();

        comment.put("user_id", Integer.toString(p.getpID()));
        comment.put("name", p.getpName());
        comment.put("price", Float.toString(p.getpPrice()));
        comment.put("description", p.getpDescription());
        comment.put("waitlist", Integer.toString(p.getpWaiting()));
        comment.put("image_id", Integer.toString(p.getImageId()));
        comment.put("group_id", Integer.toString(p.getGroupId()));
        comment.put("category", p.getCategory());
        comment.put("status", p.getStatus());

        //Convert java object to json with external library "gson"
        String json = new GsonBuilder().create().toJson(comment, Map.class);

        Log.d("[jsonHTTPPut]", json);

        return json;
    }
}
