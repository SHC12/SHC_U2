package com.mobile.ukd.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mobile.ukd.R;
import com.mobile.ukd.model.Pembayaran;

import java.util.List;

public class TableAdapterRiwayat extends RecyclerView.Adapter {
    private Context mContext;
    private List<Pembayaran> pembayaranList;

    public TableAdapterRiwayat(List<Pembayaran> pembayaranList, Context context) {
        this.pembayaranList = pembayaranList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.riwayat_peminjaman_items, parent, false);

        return new RowViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {

            rowViewHolder.txtPembayaranKe.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtTanggalPembayaran.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtNominalPembayaran.setBackgroundResource(R.drawable.table_header_cell_bg);

            rowViewHolder.txtPembayaranKe.setTextColor(Color.WHITE);
            rowViewHolder.txtTanggalPembayaran.setTextColor(Color.WHITE);
            rowViewHolder.txtNominalPembayaran.setTextColor(Color.WHITE);

            rowViewHolder.txtPembayaranKe.setText("Ke");
            rowViewHolder.txtTanggalPembayaran.setText("Tanggal");
            rowViewHolder.txtNominalPembayaran.setText("Nominal");
        } else {
            Pembayaran modal = pembayaranList.get(rowPos - 1);

            rowViewHolder.txtPembayaranKe.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtTanggalPembayaran.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtNominalPembayaran.setBackgroundResource(R.drawable.table_content_cell_bg);

            rowViewHolder.txtPembayaranKe.setTextColor(Color.BLACK);
            rowViewHolder.txtTanggalPembayaran.setTextColor(Color.BLACK);
            rowViewHolder.txtNominalPembayaran.setTextColor(Color.BLACK);

            rowViewHolder.txtPembayaranKe.setText(modal.getPembayaranKe() + "");
            rowViewHolder.txtTanggalPembayaran.setText(modal.getTanggalPembayaran());
            rowViewHolder.txtNominalPembayaran.setText(modal.getNominalPembayaran());
        }
    }

    @Override
    public int getItemCount() {
        return pembayaranList.size()+1;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        TextView txtPembayaranKe;
        TextView txtTanggalPembayaran;
        TextView txtNominalPembayaran;

        RowViewHolder(View itemView) {
            super(itemView);
            txtPembayaranKe = itemView.findViewById(R.id.txt_pembayaran_ke);
            txtTanggalPembayaran = itemView.findViewById(R.id.txt_tanggal_pembayaran);
            txtNominalPembayaran = itemView.findViewById(R.id.txt_nominal_pembayaran);
        }
    }
}
