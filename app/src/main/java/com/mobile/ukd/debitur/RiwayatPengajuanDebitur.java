package com.mobile.ukd.debitur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.mobile.ukd.admin.DataPembayaranDebitur;
import com.mobile.ukd.admin.DetailDataPembayaranDebitur;
import com.mobile.ukd.model.Pembayaran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RiwayatPengajuanDebitur extends AppCompatActivity {
    private String URL_RIWAYAT_PEMBAYARAN = "http://kristoforus.my.id/api_android/rincian_pembayaran.php?id_user=";
    private String URL_DETAIL_PEMBAYARAN = "http://kristoforus.my.id/api_android/detail_data_pembayaran_debitur.php?id_user=";
    private ArrayList<Pembayaran> riwayatList = new ArrayList<>();
    private RecyclerView rvRiwayatDebitur;
    private TableAdapterRiwayat adapter;
    private TextView totalBayar;
    public static final String PREFS_NAME = "MyPrefsFile";
    private String URL_LENGKAP,URL_LENGKAP_TOTAL,idUser, id_debitur_user, nama, URL_LENGKAP_PDF;
    String url_rincian_debitur = "http://kristoforus.my.id/api_android/export_debitur.php?id_debitur=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pengajuan_debitur);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        idUser = settings.getString("id_user", "default");
        id_debitur_user = settings.getString("id_debitur_user", "default");
        nama = settings.getString("nama", "default");
        URL_LENGKAP = URL_RIWAYAT_PEMBAYARAN+idUser;
        URL_LENGKAP_TOTAL = URL_DETAIL_PEMBAYARAN+idUser;
        URL_LENGKAP_PDF = url_rincian_debitur + id_debitur_user;
        rvRiwayatDebitur = findViewById(R.id.rv_riwayat_peminjaman);
        adapter = new TableAdapterRiwayat(riwayatList, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvRiwayatDebitur.setLayoutManager(linearLayoutManager);
        rvRiwayatDebitur.setHasFixedSize(true);
        rvRiwayatDebitur.setAdapter(adapter);
        totalBayar = findViewById(R.id.txt_total_bayar_riwayat);
        getRiwayatPembayaran();
        getDataTotal();

    }

    public void getRiwayatPembayaran(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_LENGKAP, new Response.Listener<JSONArray>() {
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
                            if(riwayatList.isEmpty()){
                                Toast.makeText(RiwayatPengajuanDebitur.this, "Tidak ada data", Toast.LENGTH_SHORT).show();

                            }

                            adapter = new TableAdapterRiwayat(riwayatList, getApplicationContext());
                            rvRiwayatDebitur.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(RiwayatPengajuanDebitur.this, "Anda belum memiliki riwayat pembayaran",Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RiwayatPengajuanDebitur.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
    public void getDataTotal(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_LENGKAP_TOTAL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    Log.e("api detail", response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            String s_total_bayar = jsonObject.getString("total_bayar");
                            totalBayar.setText("Total Bayar : "+s_total_bayar);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(RiwayatPengajuanDebitur.this, "Response Kosong",Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RiwayatPengajuanDebitur.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    public void toExportRiwayat(View view) {
        export_rincian_debitur(URL_LENGKAP_PDF);
    }

    public void export_rincian_debitur(String aUrl) {
        AndroidNetworking.download(aUrl, "/storage/emulated/0/Download/", "detail_rincian_debitur_"+nama+".pdf")
                .setTag("downloadTest")
                .setPriority(Priority.MEDIUM)
                .addHeaders("Authorization", "Basic YnNyZTpzZWN1cmV0cmFuc2FjdGlvbnMhISE=")
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        Toast.makeText(RiwayatPengajuanDebitur.this, "Mengunduh File", Toast.LENGTH_SHORT).show();
                        // do anything with progress
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
//                        progressDialog.dismiss();
                        Toast.makeText(RiwayatPengajuanDebitur.this, "detail_calon_debitur"+nama+".pdf"+" selesai di download", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
}
