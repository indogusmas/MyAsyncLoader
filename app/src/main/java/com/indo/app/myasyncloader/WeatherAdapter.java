package com.indo.app.myasyncloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by indo on 08/01/18.
 */

public class WeatherAdapter extends BaseAdapter {

    private ArrayList<WeatherItems> mdata = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public WeatherAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void setData(ArrayList<WeatherItems> items){
        mdata = items;
        notifyDataSetChanged();
    }
    public void adItem(final  WeatherItems item){
        mdata.add(item);
        notifyDataSetChanged();
    }
    public void clearData(){
        mdata.clear();
    }

    @Override
    public int getCount() {
        if (mdata == null)
            return 0;
        return mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.weather_items, null);
            holder.textViewNamaKota = (TextView)convertView.findViewById(R.id.textkota);
            holder.textViewTemperature = (TextView)convertView.findViewById(R.id.texttemp);
            holder.textViewDescription = (TextView)convertView.findViewById(R.id.textDesc);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewNamaKota.setText(mdata.get(position).getNama());
        holder.textViewTemperature.setText(mdata.get(position).getTemperature());
        holder.textViewDescription.setText(mdata.get(position).getDescription());
        return convertView;

    }

    private static class ViewHolder{
        TextView textViewNamaKota;
        TextView textViewTemperature;
        TextView textViewDescription;
    }
}
