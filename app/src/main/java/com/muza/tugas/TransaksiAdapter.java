package com.muza.tugas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {
    private List<Transaksi> mTransaksi;
    private Context mContext;


    public TransaksiAdapter(List<Transaksi> mTransaksi, Context mContext) {
        this.mTransaksi = mTransaksi;
        this.mContext = mContext;
    }


    public void addItem (Transaksi newtransaksi) {
        this.mTransaksi.add(newtransaksi);
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
        Transaksi transaksi = mTransaksi.get(position);

        // Set item views based on your views and data model
        holder.title.setText(transaksi.getKeterangan());

        holder.amount.setText(transaksi.getJumlah());


        String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd");
        holder.tglTrx.setText(dateFormatter.format(timeStamp));
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    // Inner class for view holder
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView amount;
        TextView tglTrx;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            amount = (TextView) itemView.findViewById(R.id.amount);
            tglTrx = (TextView) itemView.findViewById(R.id.tglTrx);

        }
    }
}