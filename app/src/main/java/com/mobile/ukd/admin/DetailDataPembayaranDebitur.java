package com.mobile.ukd.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.mobile.ukd.R;
import com.mobile.ukd.adapter.TableAdapterRiwayat;
import com.mobile.ukd.model.Debitur;
import com.mobile.ukd.model.Pembayaran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailDataPembayaranDebitur extends AppCompatActivity {

    public static final String DETAIL_PEMBAYARAN_DEBITUR = "detail_pembayaran_debitur";
    String URL_DETAIL_PEMBAYARAN = "http://kristoforus.my.id/api_android/detail_data_pembayaran_debitur.php?id_user=";
    String URL_RIWAYAT_PEMBAYARAN = "http://kristoforus.my.id/api_android/rincian_pembayaran.php?id_user=";
    String URL_LENGKAP;
    String URL_LENGKAP_PEMBAYARAN;
    String id_debitur, id_user, id_pengajuan;

    TextView nama, pinjaman, total_bayar, sisa_hutang, kode_debitur;
    String url_rincian_debitur = "http://kristoforus.my.id/api_android/export_debitur.php?id_debitur=";
    String URL_EXPORT_RINCIAN;
    private ArrayList<Pembayaran> riwayatList = new ArrayList<>();
    private RecyclerView rvRiwayatDebitur;
    private TableAdapterRiwayat adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data_pembayaran_debitur);

        Debitur debitur = getIntent().getParcelableExtra(DETAIL_PEMBAYARAN_DEBITUR);
        id_user = debitur.getId_user();
        id_debitur = debitur.getKodeDebitur();
        id_pengajuan = debitur.getIdDebitur();
        URL_LENGKAP = URL_DETAIL_PEMBAYARAN + id_user;
        URL_LENGKAP_PEMBAYARAN = URL_RIWAYAT_PEMBAYARAN + id_user;

        URL_EXPORT_RINCIAN = url_rincian_debitur + id_pengajuan;

        rvRiwayatDebitur = findViewById(R.id.rv_rincian_pembayaran);
        adapter = new TableAdapterRiwayat(riwayatList, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvRiwayatDebitur.setLayoutManager(linearLayoutManager);
        rvRiwayatDebitur.setHasFixedSize(true);
        rvRiwayatDebitur.setAdapter(adapter);


        kode_debitur = findViewById(R.id.tx_kode_debitur_detail_pembayaran);
        kode_debitur.setText("Kode Debitur : " + id_debitur);
        nama = findViewById(R.id.namaDetailPembayaranDebitur);
        nama.setText(debitur.getNamaDebitur());
        pinjaman = findViewById(R.id.pinjamanPembayaranDebitur);
        total_bayar = findViewById(R.id.totalBayarPeminjamanDebitur);
        sisa_hutang = findViewById(R.id.sisaHutangPembayaranDebitur);

        getDataDetail();
        getRiwayatPembayaran();
    }


    public void getDataDetail() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_LENGKAP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    Log.e("api detail", response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            String s_pinjaman = jsonObject.getString("pinjaman");
                            String s_total_bayar = jsonObject.getString("total_bayar");
                            String s_sisa_hutang = jsonObject.getString("sisa_hutang");
                            pinjaman.setText(s_pinjaman);
                            total_bayar.setText(s_total_bayar);
                            sisa_hutang.setText(s_sisa_hutang);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(DetailDataPembayaranDebitur.this, "Response Kosong", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailDataPembayaranDebitur.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    public void getRiwayatPembayaran() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_LENGKAP_PEMBAYARAN, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    Log.e("api detail", response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Pembayaran pembayaran = new Pembayaran();

                            pembayaran.setPembayaranKe(jsonObject.getString("no"));
                            pembayaran.setNominalPembayaran(jsonObject.getString("nominal_debitur"));
                            pembayaran.setTanggalPembayaran(jsonObject.getString("tanggal_pembayaran"));


                            riwayatList.add(pembayaran);
                            adapter = new TableAdapterRiwayat(riwayatList, getApplicationContext());
                            rvRiwayatDebitur.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(DetailDataPembayaranDebitur.this, "Anda belum memiliki riwayat pembayaran", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailDataPembayaranDebitur.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    public void toExportDetailRincianPembayaranDebitur(View view) {
        export_rincian_debitur(URL_EXPORT_RINCIAN);
    }

    public void export_rincian_debitur(String aUrl) {
        AndroidNetworking.download(aUrl, "/storage/emulated/0/Download/", "detail_rincian_debitur_" + id_debitur + ".pdf")
                .setTag("downloadTest")
                .setPriority(Priority.MEDIUM)
                .addHeaders("Authorization", "Basic YnNyZTpzZWN1cmV0cmFuc2FjdGlvbnMhISE=")
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        Toast.makeText(DetailDataPembayaranDebitur.this, "Mengunduh File", Toast.LENGTH_SHORT).show();
                        // do anything with progress
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
//                        progressDialog.dismiss();
                        Toast.makeText(DetailDataPembayaranDebitur.this, "detail_rincian_debitur_" + id_debitur + ".pdf"+" selesai di download", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
}
