package com.indo.app.myasyncloader;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<WeatherItems>>{

    ListView listView;
    WeatherAdapter adapter;
    EditText editkota;
    Button btnCari;
    static  final String EXTRAS_CITY = "EXSTRA_CITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new WeatherAdapter(this);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        editkota = (EditText)findViewById(R.id.edit_kota);
        btnCari = (Button)findViewById(R.id.btn_kota);
        btnCari.setOnClickListener(myListener);

        String kota = editkota.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_CITY, kota);
        getLoaderManager().initLoader(0, bundle, this);

    }


    @Override
    public Loader<ArrayList<WeatherItems>> onCreateLoader(int id, Bundle args) {
        String kumpulankota = "";
        if (args != null){
            kumpulankota = args.getString(EXTRAS_CITY);
        }
        return new MyAsycTaskLoader(this, kumpulankota);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<WeatherItems>> loader, ArrayList<WeatherItems> data) {
        adapter.setData(data);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeatherItems>> loader) {
        adapter.setData(null);

    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String kota = editkota.getText().toString();
            if (TextUtils.isEmpty(kota))
                return;
            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_CITY, kota);
            getLoaderManager().restartLoader(0, bundle, MainActivity.this);
        }
    };
}
