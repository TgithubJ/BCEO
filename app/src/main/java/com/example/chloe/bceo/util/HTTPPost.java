package com.example.chloe.bceo.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
    String json;
    private int image_ID;
    private String url;

    public int getImage_ID(){
        return image_ID;
    }

    public void setURL(String url){
        this.url = url;
    }

    public String getURL(){
        return this.url;
    }

    public void executeImageUpload(String strBm64Base) {
        Map<String, String> comment = new HashMap<String, String>();

        comment.put("image", strBm64Base);

        //Convert java object to json with external library "gson"
        json = new GsonBuilder().create().toJson(comment, Map.class);

        setURL("http://52.34.169.54:3000/images");
        new Connection().execute();
    }

    public void uploadProduct(int user_id, String pName, float pPrice, String pDescription, int pWaiting, int imageId, int groupId, String category, String status) {
        Map<String, String> comment = new HashMap<String, String>();

        comment.put("user_id", Integer.toString(user_id));
        comment.put("name", pName);
        comment.put("price", Float.toString(pPrice));
        comment.put("description", pDescription);
        comment.put("waitlist", Integer.toString(pWaiting));
        comment.put("image_id", Integer.toString(imageId));
        comment.put("group_id", Integer.toString(groupId));
        comment.put("category", category);
        comment.put("status", status);

        //Convert java object to json with external library "gson"
        json = new GsonBuilder().create().toJson(comment, Map.class);

        Log.d("[jsonProd]", json);

        setURL("http://52.34.169.54:3000/products");
        new Connection().execute();

    }

    public Bitmap executeImageDownload(String productID) throws IOException{
        Bitmap bm = null;

        //Make a HTTP request
        HttpResponse resHTTP = makeRequest("http://52.34.169.54:3000/images?id=15");

        if (resHTTP.getStatusLine().getStatusCode() == 200) {
            // Get Result string from HTTP entity
            String jsonResult = EntityUtils.toString(resHTTP.getEntity());
            bm = Image64Base.decodeBase64(jsonResult);
        } else {
            Log.e("httpPost", "Post Request failed!");
        }

        return bm;
    }

    private class Connection extends AsyncTask {
        @Override
        protected Object doInBackground(Object... arg0) {
            HttpResponse httpResponse = makeRequest(url);
            try {
                String imageID = EntityUtils.toString(httpResponse.getEntity());
                image_ID = Integer.parseInt(imageID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public HttpResponse makeRequest(String uri) {
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
