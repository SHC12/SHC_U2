package com.mobile.ukd.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.ukd.R;
import com.mobile.ukd.model.Debitur;

import java.util.List;

public class TableAdapterDebitur extends RecyclerView.Adapter {
    private Context mContext;
    private List<Debitur> debiturList;
    private RecyclerViewClickListener mListener;

    public TableAdapterDebitur(List<Debitur> debiturList, Context context,RecyclerViewClickListener listener) {
        this.debiturList = debiturList;
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.data_debitur_items, parent, false);

        return new RowViewHolder(itemView,mListener);
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {

            rowViewHolder.txtNo.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtKode.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtNama.setBackgroundResource(R.drawable.table_header_cell_bg);

            rowViewHolder.txtNo.setTextColor(Color.WHITE);
            rowViewHolder.txtKode.setTextColor(Color.WHITE);
            rowViewHolder.txtNama.setTextColor(Color.WHITE);

            rowViewHolder.txtNo.setText("No");
            rowViewHolder.txtKode.setText("Kode Debitur");
            rowViewHolder.txtNama.setText("Nama");
        } else {
            Debitur modal = debiturList.get(rowPos - 1);

            rowViewHolder.txtNo.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtKode.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtNama.setBackgroundResource(R.drawable.table_content_cell_bg);

            rowViewHolder.txtNo.setTextColor(Color.BLACK);
            rowViewHolder.txtKode.setTextColor(Color.BLACK);
            rowViewHolder.txtNama.setTextColor(Color.BLACK);
            String first[] = modal.getNamaDebitur().split(" ", 2);
            rowViewHolder.txtNo.setText(modal.getNo() + "");
            rowViewHolder.txtKode.setText(modal.getKodeDebitur());
            rowViewHolder.txtNama.setText(first[0]);

        }
    }

    @Override
    public int getItemCount() {
        return debiturList.size()+1;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNo;
        TextView txtKode;
        TextView txtNama;
        RelativeLayout rvItems;
        RecyclerViewClickListener mListener;
        RowViewHolder(View itemView,RecyclerViewClickListener listener) {
            super(itemView);
            txtNo = itemView.findViewById(R.id.txt_no_data_debitur);
            txtKode = itemView.findViewById(R.id.txt_kode_debitur_data_debitur);
            txtNama = itemView.findViewById(R.id.txt_nama_data_debitur);
            rvItems = itemView.findViewById(R.id.rv_item);
            mListener = listener;
            rvItems.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rv_item:
                    mListener.onRowClick(rvItems,getAdapterPosition());
                    break;

                default:
                    break;
            }

        }
    }
}
