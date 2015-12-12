package com.example.chloe.bceo.util;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chloeshim on 12/7/15.
 */
public class HTTPGet {

    public static String buildURL(String tail) {
        // remote server address
        StringBuilder urlStr =
                new StringBuilder("http://52.34.169.54:3000/");
        urlStr.append(tail);

        return urlStr.toString();
    }

    // GET response from server
    public static String getResponse(String urlStr) {
        URL url;
        HttpURLConnection urlConnection = null;

        // server response str
        StringBuilder response = new StringBuilder();
        try {
            url = new URL(urlStr.toString());
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
                e.printStackTrace();
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
}
