package com.example.rika.spk.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rika.spk.R;
import com.example.rika.spk.data.Data;

import java.util.List;

/**
 Created by Rika on 11/8/2016.
 */

public class AdapterIkan extends ArrayAdapter<Data> {

    private Context context;
    private List<Data> ListIkan;

    public AdapterIkan(Context context, int resource, List<Data> objects) {
        super(context, resource, objects);
        this.context = context;
        this.ListIkan = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.list_row, parent, false);
        Data ikan = ListIkan.get(position);

        TextView nama_ikan = (TextView) view.findViewById(R.id.nama_ikan);
        TextView id = (TextView) view.findViewById(R.id.id);

        id.setText(ikan.getId());
        nama_ikan.setText(ikan.getNama_ikan());

        return view;
    }
}
