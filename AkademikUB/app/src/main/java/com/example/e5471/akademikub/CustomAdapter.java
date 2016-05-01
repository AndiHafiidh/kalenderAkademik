package com.example.e5471.akademikub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by E5471 on 01/05/2016.
 */
public class CustomAdapter  extends ArrayAdapter<Jadwal> {

        public Context context;
        public ArrayList<Jadwal> listJadwal = null;

        public CustomAdapter(Context context, ArrayList<Jadwal> listJadwal) {//Constructoradapter
            super(context, R.layout.activity_adapter, listJadwal);
            this.context = context;
            this.listJadwal = listJadwal;
        }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//modifygetView()
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_adapter, parent, false);


        TextView tgl = (TextView) rowView.findViewById(R.id.tgl);
        tgl.setText(listJadwal.get(position).tanggal);


        TextView bulan = (TextView) rowView.findViewById(R.id.bulan);
        bulan.setText(listJadwal.get(position).bulan);


        TextView tahun = (TextView) rowView.findViewById(R.id.tahun);
        tahun.setText(listJadwal.get(position).tahun);

        TextView judul = (TextView) rowView.findViewById(R.id.judul);
        judul.setText(listJadwal.get(position).judul);

        TextView keterangan = (TextView) rowView.findViewById(R.id.keterangan);
        keterangan.setText(listJadwal.get(position).keterangan);

        return (rowView);
    }
}

