package com.example.service_base;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

class JSONParser {

    //main code
    public static final int GET = 300;
    public static final int POST = 301;
    private static final int TIMEOUT = 2000;

    static JSONObject jObj = null;


    // constructor
    public JSONParser()  {

    }


    public JSONObject makeHttpRequest(String url, int method) throws JSONException {

        String json_obj_string = null;
        HttpURLConnection data_con = null;


        if (method == GET)
        {
            try {
                URL myUrl = new URL(url);
                data_con = (HttpURLConnection) myUrl.openConnection();
                data_con.setRequestMethod("GET");
                data_con.setRequestProperty("Content-length", "0");
                data_con.setUseCaches(false);
                data_con.setAllowUserInteraction(false);
                data_con.setConnectTimeout(TIMEOUT);
                data_con.setReadTimeout(TIMEOUT);
                data_con.connect();
                int status = data_con.getResponseCode();
                if (data_con.getResponseCode() == HttpURLConnection.HTTP_CREATED || data_con.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(data_con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    //return sb.toString();
                    json_obj_string = sb.toString();
                }

            } catch (MalformedURLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            }finally {
                if (data_con != null) {
                    try {
                        data_con.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            Log.d("JSONParcer GET", json_obj_string);
        }


        jObj = new JSONObject(json_obj_string);

        return jObj;
    }


    }

