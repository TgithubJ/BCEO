package com.example.chloe.bceo.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chloeshim on 12/7/15.
 */
public class HTTPGet {

    public static String buildURL(String tail) {
        StringBuilder urlStr =
                new StringBuilder("http://52.34.169.54:3000/");
        urlStr.append(tail);

        return urlStr.toString();
    }

    public static String getResponse(String urlStr) {
        URL url;
        HttpURLConnection urlConnection = null;

        // server response str
        StringBuilder response = new StringBuilder();
        try {
            url = new URL(urlStr.toString());
            Log.d("[HTTP Get]", urlStr.toString());
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);


            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                response.append(current);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }

        return response.toString();
    }

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }


//    private class ConnectionGet extends AsyncTask {
//
//        @Override
//        protected Object doInBackground(Object... arg0) {
//            String res = getResponse("http://52.34.169.54:3000/images?id=15");
//
//            return null;
//        }
//    }
//
//    public HttpResponse makeRequest(String uri) {
//        try {
//
//            HttpPost httpPost = new HttpPost(uri);
//            httpPost.setEntity(new StringEntity(json));
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-type", "application/json");
//
//            return new DefaultHttpClient().execute(httpPost);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
