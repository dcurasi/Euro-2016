package com.example.dario.euro2016;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;


public class EuroIntentService extends IntentService {

    private static final String TAG = "IntentService";

    public static final String REQUEST_TYPE = "requestType";

    public static final int TYPE_TORNEO = 1;
    public static final int TYPE_PARTITE = 2;
    public static final int TYPE_CLASSIFICHE = 4;
    public static final String BASE_URL = "http://api.football-data.org/v1/competitions/";
    public static final String EUROPEO_PARAM = "424";
    public static final String PARTITE_PARAM = "fixtures";
    public static final String CLASSIFICHE_PARAM = "leagueTable";

    public EuroIntentService() {
        super("EuroIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        if(extras==null)return;

        final int requestType = extras.getInt(REQUEST_TYPE, -1);
        Uri builtUri;
        String url;
        HttpURLConnection connection = null;

        switch (requestType) {

            case TYPE_TORNEO:
                String torneoJsonStr;

                builtUri = Uri.parse(BASE_URL + EUROPEO_PARAM).buildUpon().build();
                url = builtUri.toString();
                try {
                    connection = HttpUtils.buildConnection(url, HttpUtils.METHOD_GET, null);
                    InputStream inputStream = connection.getInputStream();
                    torneoJsonStr = HttpUtils.readIt(inputStream);
                } catch (IOException e) {
                    Log.e(TAG, "Error Torneo", e);
                    torneoJsonStr = null;
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                try {
                    Parser.getTorneoDataFromJson(EuroIntentService.this, torneoJsonStr);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage(), e);
                    e.printStackTrace();
                }

                break;

            case TYPE_PARTITE:
                String partiteJsonStr;

                builtUri = Uri.parse(BASE_URL + EUROPEO_PARAM + "/" + PARTITE_PARAM).buildUpon().build();
                url = builtUri.toString();
                try {
                    connection = HttpUtils.buildConnection(url, HttpUtils.METHOD_GET, null);
                    InputStream inputStream = connection.getInputStream();
                    partiteJsonStr = HttpUtils.readIt(inputStream);
                } catch (IOException e) {
                    Log.e(TAG, "Error Partite", e);
                    partiteJsonStr = null;
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                try {
                    Parser.getPartiteDataFromJson(EuroIntentService.this, partiteJsonStr);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage(), e);
                    e.printStackTrace();
                }

                break;

            case TYPE_CLASSIFICHE:

                String classificheJsonStr;

                builtUri = Uri.parse(BASE_URL + EUROPEO_PARAM + "/" + CLASSIFICHE_PARAM).buildUpon().build();
                url = builtUri.toString();
                try {
                    connection = HttpUtils.buildConnection(url, HttpUtils.METHOD_GET, null);
                    InputStream inputStream = connection.getInputStream();
                    classificheJsonStr = HttpUtils.readIt(inputStream);
                } catch (IOException e) {
                    Log.e(TAG, "Error Classifiche", e);
                    classificheJsonStr = null;
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                try {
                    Parser.getClassificheDataFromJson(EuroIntentService.this, classificheJsonStr);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage(), e);
                    e.printStackTrace();
                }

                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(MainActivityFragment.ResponseReceiver.ACTION_RESP);
                sendBroadcast(broadcastIntent);

                break;
        }

    }

}
