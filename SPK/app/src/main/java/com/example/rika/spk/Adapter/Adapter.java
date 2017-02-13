package com.example.rika.spk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rika.spk.R;
import com.example.rika.spk.data.Data;

import java.util.List;

/**
 * Created by Rika on 11/8/2016.
 */
public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data> items;

    public Adapter(Activity activity, List<Data> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);


        TextView nama_ikan = (TextView) convertView.findViewById(R.id.nama_ikan);
        TextView id = (TextView) convertView.findViewById(R.id.id);
        /*TextView url_gambar = (TextView) convertView.findViewById(R.id.url_gambar);
        TextView deskripsi = (TextView) convertView.findViewById(R.id.deskripsi);
        TextView suhu = (TextView) convertView.findViewById(R.id.suhu);
        TextView ph = (TextView) convertView.findViewById(R.id.ph);
        TextView oksigen = (TextView) convertView.findViewById(R.id.oksigen);
        TextView lama_ikan = (TextView) convertView.findViewById(R.id.lama_ikan);
        TextView luas_kolam = (TextView) convertView.findViewById(R.id.luas_kolam);
        TextView minat_masy = (TextView) convertView.findViewById(R.id.minat_masy);
        TextView tinggi_darat = (TextView) convertView.findViewById(R.id.tinggi_darat);
        TextView mudah_pakan = (TextView) convertView.findViewById(R.id.mudah_pakan);*/
        Data data = items.get(position);

        nama_ikan.setText(data.getNama_ikan());
        id.setText(data.getId());
        //url_gambar.setText(data.getGambar());
        //deskripsi.setText(data.getDESKRIPSI());
        //suhu.setText(data.getSUHU());
        //ph.setText(data.getPH());
        //oksigen.setText(data.getOKSIGEN());
        //lama_ikan.setText(data.getLAMA());
        //luas_kolam.setText(data.getLUAS());
        //minat_masy.setText(data.getMINAT());
        //tinggi_darat.setText(data.getTINNGI());
        //mudah_pakan.setText(data.getMUDAH());
        return convertView;
    }
}
