package com.example.service_base;

import android.content.ContentValues;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

class JSONParser {

    //main code
    public static final int GET = 300;
    public static final int POST = 301;
    private static final int TIMEOUT_CONNECT = 3000;
    private static final int TIMEOUT_READ = 10000;

    static JSONObject jObj = null;


    // constructor
    public JSONParser() {

    }


    public JSONObject makeHttpRequest(String url, int method, ContentValues param) throws JSONException {

        String json_obj_string = null;
        HttpURLConnection data_con = null;
        try {

            if (method == GET) {
                URL myUrl = new URL(url + "?" + getQuery(param));
                data_con = (HttpURLConnection) myUrl.openConnection();
                data_con.setRequestMethod("GET");
                data_con.setRequestProperty("Content-length", "0");
                data_con.setUseCaches(false);
                data_con.setAllowUserInteraction(false);
                data_con.setConnectTimeout(TIMEOUT_CONNECT);
                data_con.setReadTimeout(TIMEOUT_READ);
                data_con.connect();
            } else if (method == POST) {
                URL myUrl = new URL(url);
                data_con = (HttpURLConnection) myUrl.openConnection();
                data_con.setReadTimeout(TIMEOUT_READ);
                data_con.setConnectTimeout(TIMEOUT_CONNECT);
                data_con.setRequestMethod("POST");
                data_con.setDoInput(true);
                data_con.setDoOutput(true);
                OutputStream os = data_con.getOutputStream();
                os.write(getQuery(param).getBytes("UTF-8"));
                os.close();
                data_con.connect();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
        if (data_con.getResponseCode() == HttpURLConnection.HTTP_CREATED || data_con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(data_con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            //return sb.toString();
            json_obj_string = sb.toString();
        }
        else {
            json_obj_string = String.valueOf("\"success\":0");
        }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        if (data_con != null) {
            try {
                data_con.disconnect();
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

        Log.d("JSONParcer GET", json_obj_string);

        jObj = new JSONObject(json_obj_string);

        return jObj;
    }


    public String getQuery(ContentValues param) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Object> entry : param.valueSet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
        }
        return result.toString();
    }


    }

