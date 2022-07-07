package com.muza.tugas;

import static java.lang.System.currentTimeMillis;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import java.security.Timestamp;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
        private List<ModelTransaksi> mTransaksi;
        private  Context mContext;


        public MainAdapter(List<ModelTransaksi> mTransaksi, Context mContext) {
            this.mTransaksi = mTransaksi;
            this.mContext = mContext;
        }

        public void addItem (ModelTransaksi newTransaksi) {

            this.mTransaksi.add(newTransaksi);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);

            // Inflate the custom layout
            View transaksiView = inflater.inflate(R.layout.item_transaksi, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(transaksiView);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // Get the data model based on position
            ModelTransaksi transaksi = mTransaksi.get(position);

            // Set item views based on your views and data model
            holder.keterangan.setText(transaksi.getKeterangan());

            holder.jumlah.setText(transaksi.getJumlah());

            holder.tip.setText(transaksi.getTipe_transaksi());
        }

        @Override
        public int getItemCount() {
            return mTransaksi.size();
        }

        // Inner class for view holder
        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView keterangan;
            TextView jumlah;
            TextView tip;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                keterangan = (TextView) itemView.findViewById(R.id.title);
                jumlah = (TextView) itemView.findViewById(R.id.amount);
                tip = (TextView) itemView.findViewById(R.id.tglTrx);

            }
        }
    }

