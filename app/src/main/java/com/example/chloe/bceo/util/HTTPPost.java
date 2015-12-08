package com.example.chloe.bceo.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by HsiangLin on 12/5/15.
 */
public class HTTPPost {

    public final static int uploadImage = 11;
    public final static int requestImage = 22;
    String json;

    public void executeImageUpload(String strBm64Base) {
        Map<String, String> comment = new HashMap<String, String>();

        comment.put("image", strBm64Base);

        //Convert java object to json with external library "gson"
        json = new GsonBuilder().create().toJson(comment, Map.class);

        Log.d("[json]", json);

//        Connection connect = new Connection("http://52.34.169.54:3000/images", json);
//        connect.execute();
        new Connection().execute();

        //Make a HTTP request
//        HttpResponse resHTTP = makeRequest("http://52.34.169.54:3000/images", json);
    }

    public Bitmap executeImageDownload(String productID) throws IOException{
        Bitmap bm = null;
//        Map<String, String> comment = new HashMap<String, String>();

//        comment.put("product_id", productID);

//        //Convert java object to json with external library "gson"
//        String json = new GsonBuilder().create().toJson(comment, Map.class);

        //Make a HTTP request
        HttpResponse resHTTP = makeRequest("http://52.34.169.54:3000/images?id=15");

        if (resHTTP.getStatusLine().getStatusCode() == 200) {
            // Get Result string from HTTP entity
            String jsonResult = EntityUtils.toString(resHTTP.getEntity());
            Log.d("[HttpPost Decode]", "jsonResult");

            //Convert json to map
//            Gson gson = new Gson();
//            Map<String, String> resBitmap = gson.fromJson(jsonResult, new TypeToken<Map<String, String>>(){}.getType());

            // Decode string back to bitmap and return
//            bm = Image64Base.decodeBase64(resBitmap.get("image64str"));
            bm = Image64Base.decodeBase64(jsonResult);

        } else {
            Log.e("httpPost", "Post Request failed!");
        }

        return bm;
    }

    private class Connection extends AsyncTask {

        @Override
        protected Object doInBackground(Object... arg0) {
//            makeRequest(url, json);
            makeRequest("http://52.34.169.54:3000/images");
            return null;
        }
    }

    public HttpResponse makeRequest(String uri) {
        try {
//            HttpParams params = new BasicHttpParams();
//            params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//            HttpClient httpClient = new DefaultHttpClient(params);

            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//            nameValuePairs.add(new BasicNameValuePair("image", "testingforhttppost"));
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

//            return httpClient.execute(httpPost);
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
