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

public class TableAdapterCalonDebitur extends RecyclerView.Adapter {
    private Context mContext;
    private List<Debitur> debiturList;
    private RecyclerViewClickListener mListener;

    public TableAdapterCalonDebitur(List<Debitur> debiturList, Context context, RecyclerViewClickListener listener) {
        this.debiturList = debiturList;
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.data_calon_debitur_items, parent, false);

        return new RowViewHolder(itemView, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {

            rowViewHolder.txtNo.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtNama.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtNominal.setBackgroundResource(R.drawable.table_header_cell_bg);

            rowViewHolder.txtNo.setTextColor(Color.WHITE);
            rowViewHolder.txtNama.setTextColor(Color.WHITE);
            rowViewHolder.txtNominal.setTextColor(Color.WHITE);

            rowViewHolder.txtNo.setText("No");
            rowViewHolder.txtNama.setText("Nama");
            rowViewHolder.txtNominal.setText("Nominal");
        } else {
            Debitur modal = debiturList.get(rowPos - 1);

            rowViewHolder.txtNo.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtNama.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtNominal.setBackgroundResource(R.drawable.table_content_cell_bg);

            rowViewHolder.txtNo.setTextColor(Color.BLACK);
            rowViewHolder.txtNama.setTextColor(Color.BLACK);
            rowViewHolder.txtNominal.setTextColor(Color.BLACK);
            String first[] = modal.getNamaDebitur().split(" ", 2);
            rowViewHolder.txtNo.setText(modal.getNo() + "");
            rowViewHolder.txtNama.setText(first[0] + "");
            rowViewHolder.txtNominal.setText(modal.getNominal() + "");

        }
    }

    @Override
    public int getItemCount() {
        return debiturList.size() + 1;
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    public class RowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNo;
        TextView txtNama;
        TextView txtNominal;
        RelativeLayout rvItemsCalon;
        TableAdapterCalonDebitur.RecyclerViewClickListener mListener;

        RowViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            txtNo = itemView.findViewById(R.id.txt_no);
            txtNama = itemView.findViewById(R.id.txt_nama);
            txtNominal = itemView.findViewById(R.id.txt_nominal);
            rvItemsCalon = itemView.findViewById(R.id.rv_item_calon);
            mListener = listener;
            rvItemsCalon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rv_item_calon:
                    mListener.onRowClick(rvItemsCalon, getAdapterPosition());
                    break;

                default:
                    break;
            }
        }
    }
}
