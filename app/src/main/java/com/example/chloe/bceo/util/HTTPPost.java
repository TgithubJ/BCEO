package com.example.chloe.bceo.util;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by HsiangLin on 12/5/15.
 */
public class HTTPPost {

    public static void execute(String strBm64Base) {
        Map<String, String> comment = new HashMap<String, String>();

        comment.put("subject", "Using the GSON library");
        comment.put("message", "Using libraries is convenient.");
        comment.put("image", strBm64Base);

        String json = new GsonBuilder().create().toJson(comment, Map.class);
        makeRequest("http://192.168.0.1:3000/post/77/comments", json);
    }

    public static HttpResponse makeRequest(String uri, String json) {
        try {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            return new DefaultHttpClient().execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
