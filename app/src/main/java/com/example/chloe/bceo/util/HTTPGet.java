package com.example.chloe.bceo.util;

import java.io.InputStream;
import java.io.InputStreamReader;
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
}
