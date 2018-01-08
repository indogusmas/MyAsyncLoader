package com.indo.app.myasyncloader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by indo on 08/01/18.
 */

public class MyAsycTaskLoader extends AsyncTaskLoader<ArrayList<WeatherItems>>{
    private ArrayList<WeatherItems>mdata;
    private Boolean mHasResult;
    private String mKumpulanKota;
    public MyAsycTaskLoader(final Context context, String kumpulankota) {
        super(context);
        onContentChanged();
        this.mKumpulanKota = kumpulankota;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else  if (mHasResult)
            deliverResult(mdata);
    }

    @Override
    public void deliverResult(ArrayList<WeatherItems> data) {
        mdata =data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult){
            onReleaseResources(mdata);
            mdata = null;
            mHasResult = false;
        }
    }
    private static final String API_KEY = "c963ea1922f176014be1b3b4edadd5a0";

    private void onReleaseResources(ArrayList<WeatherItems> mdata) {
    }


    @Override
    public ArrayList<WeatherItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<WeatherItems> weatherItemses = new ArrayList<>();
        String url = "http://api.openweathermap.org/data/2.5/group?id="+
                mKumpulanKota+ "&units=metric&apid="+API_KEY;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
               try {
                   String result = new String(responseBody);
                   JSONObject responseObject = new JSONObject(result);
                   JSONArray list = responseObject.getJSONArray("list");
                   for (int i = 0; i < list.length(); i++){
                       JSONObject weather = list.getJSONObject(i);
                       WeatherItems weatherItems = new WeatherItems(weather);
                       weatherItemses.add(weatherItems);
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


            }
        });
        return weatherItemses;
    }
}
