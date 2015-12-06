package com.example.chloe.bceo.util;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by HsiangLin on 12/5/15.
 */
public class HTTPPost {

    public final static int uploadImage = 11;
    public final static int requestImage = 22;

    public static void executeImageUpload(String strBm64Base) {
        Map<String, String> comment = new HashMap<String, String>();

        comment.put("image", strBm64Base);

        //Convert java object to json with external library "gson"
        String json = new GsonBuilder().create().toJson(comment, Map.class);

        //Make a HTTP request
        HttpResponse resHTTP = makeRequest("http://192.168.0.1:3000/post/77/comments", json);
    }

    public static Bitmap executeImageDownload(String productID) throws IOException{
        Map<String, String> comment = new HashMap<String, String>();

        comment.put("product_id", productID);

        //Convert java object to json with external library "gson"
        String json = new GsonBuilder().create().toJson(comment, Map.class);

        //Make a HTTP request
        HttpResponse resHTTP = makeRequest("http://192.168.0.1:3000/post/77/comments", json);

        if (resHTTP.getStatusLine().getStatusCode() == 200) {
            // Get Result string from HTTP entity
            String jsonResult = EntityUtils.toString(resHTTP.getEntity());

            //Convert json to map
            Gson gson = new Gson();
            Map<String, String> resBitmap = gson.fromJson(jsonResult, new TypeToken<Map<String, String>>(){}.getType());

            // Decode string back to bitmap and return
            return Image64Base.decodeBase64(resBitmap.get("image64str"));

        } else {
            Log.e("httpPost", "Post Request failed!");
        }


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
