package com.mobile.ukd.kabag;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.ukd.R;
import com.mobile.ukd.model.Debitur;

public class DetailDebiturAktif extends AppCompatActivity {

    public static final String DETAIL_DEBITUR_DITOLAK = "detail_debitur_ditolak";
    public static final String DETAIL_DEBITUR_AKITF =  "detail_debitur_aktif";
    private TextView kode,nama,tanggal,nomor,nominal,status,title;
    private String s_kode,s_nama,s_tanggal,s_nomor,s_nominal,s_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_debitur_aktif);

        title = findViewById(R.id.title_detail_debitur_kabag);
        String flag = getIntent().getStringExtra("flag");
        if(flag.equals("aktif")){
            title.setText("Detail Debitur Aktif");
            Debitur debitur = getIntent().getParcelableExtra(DETAIL_DEBITUR_AKITF);
            s_kode = debitur.getKodeDebitur();
            s_nama = debitur.getNamaDebitur();
            s_tanggal = debitur.getTanggalPengajuan();
            s_nomor = debitur.getNoHp();
            s_nominal = debitur.getNominal();
            s_status = debitur.getStatus();
        } else if(flag.equals("ditolak")){
            title.setText("Detail Debitur Ditolak");
            Debitur debitur = getIntent().getParcelableExtra(DETAIL_DEBITUR_DITOLAK);
            s_kode = debitur.getKodeDebitur();
            s_nama = debitur.getNamaDebitur();
            s_tanggal = debitur.getTanggalPengajuan();
            s_nomor = debitur.getNoHp();
            s_nominal = debitur.getNominal();
            s_status = debitur.getStatus();
        }
        kode = findViewById(R.id.tx_kode_debitur_aktif);
        tanggal = findViewById(R.id. tanggalPengajuanDebiturAktif);
        nama = findViewById(R.id.namaDetailDebiturAktif);
        nomor = findViewById(R.id.nomorHandphoneDebiturAktif);
        nominal = findViewById(R.id.nominal_debitur_aktif);
        status = findViewById(R.id.status_debitur_aktif);

        if(s_status.equals("2")){
            status.setText("Aktif");
        }
        if(s_status.equals("3")){
            status.setText("Ditolak");
        }

        kode.setText("Kode Pengajuan : "+s_kode);
        tanggal.setText(s_tanggal);
        nama.setText(s_nama);
        nomor.setText(s_nomor);
        nominal.setText(s_nominal);

    }
}
