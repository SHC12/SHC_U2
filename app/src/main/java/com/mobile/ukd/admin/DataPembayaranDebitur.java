package com.mobile.ukd.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.mobile.ukd.DataCalonDebitur;
import com.mobile.ukd.R;
import com.mobile.ukd.adapter.TableAdapterDebitur;
import com.mobile.ukd.api.ApiClient;
import com.mobile.ukd.api.ApiInterface;
import com.mobile.ukd.model.Debitur;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class DataPembayaranDebitur extends AppCompatActivity {
    TableAdapterDebitur.RecyclerViewClickListener listener;
    ApiInterface apiInterface;
    String url_pembayaran_debitur = "http://kristoforus.my.id/api_android/export_pembayaran_debitur.php";
    Button btnExportPembayaran;
    private RecyclerView rvDebitur;
    private TableAdapterDebitur adapter;
    private List<Debitur> debiturList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pembayaran_debitur);
        rvDebitur = findViewById(R.id.rv_data_pembayaran_debitur);

        btnExportPembayaran = findViewById(R.id.btnExportPembayaranDebitur);
        btnExportPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                export_pembayaran_debitur(url_pembayaran_debitur);
            }
        });
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        listener = new TableAdapterDebitur.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(DataPembayaranDebitur.this, DetailDataPembayaranDebitur.class);
                intent.putExtra(DetailDataPembayaranDebitur.DETAIL_PEMBAYARAN_DEBITUR, debiturList.get(position - 1));
                startActivity(intent);
            }
        };
        rvDebitur.setLayoutManager(new LinearLayoutManager(this));
        rvDebitur.setHasFixedSize(true);

        getPembayaran();
    }

    public void getPembayaran() {
        Call<List<Debitur>> callPembayaran = apiInterface.getPembayaran();
        callPembayaran.enqueue(new Callback<List<Debitur>>() {
            @Override
            public void onResponse(Call<List<Debitur>> call, retrofit2.Response<List<Debitur>> response) {
                Log.e("api", response.toString());
                debiturList = response.body();
                if (debiturList.isEmpty()) {
                    Toast.makeText(DataPembayaranDebitur.this, "Tidak ada data", Toast.LENGTH_SHORT).show();
                }
                Log.i(DataCalonDebitur.class.getSimpleName(), response.body().toString());
                adapter = new TableAdapterDebitur(debiturList, getApplicationContext(), listener);
                adapter.notifyDataSetChanged();
                rvDebitur.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Debitur>> call, Throwable t) {
                Toast.makeText(DataPembayaranDebitur.this, "Terjadi kesalahan saat memuat data, Coba periksa Koneksi Internet Anda",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void export_pembayaran_debitur(String aUrl) {
        AndroidNetworking.download(aUrl, "/storage/emulated/0/Download/", "pembayaran_debitur.pdf")
                .setTag("downloadTest")
                .setPriority(Priority.MEDIUM)
                .addHeaders("Authorization", "Basic YnNyZTpzZWN1cmV0cmFuc2FjdGlvbnMhISE=")
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        Toast.makeText(DataPembayaranDebitur.this, "Mengunduh File", Toast.LENGTH_SHORT).show();
                        // do anything with progress
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
//                        progressDialog.dismiss();
                        Toast.makeText(DataPembayaranDebitur.this, "pembayaran_debitur.pdf"+" telah selesai di download", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

//    public void getDebitur() {
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_GET_DEBITUR, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if (response.length() > 0) {
//
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            Debitur debitur = new Debitur();
//                            debitur.setNo(jsonObject.getString("no"));
//                            debitur.setKodeDebitur(jsonObject.getString("id_format_debitur"));
//                            debitur.setId_user(jsonObject.getString("id_user"));
//                            debitur.setNamaDebitur(jsonObject.getString("nama_lengkap"));
//
//
//
//                            Log.e("api", response.toString());
//
//                            debiturList.add(debitur);
//                            adapter = new TableAdapterDebitur(debiturList,getApplicationContext(),listener);
//                            rvDebitur.setAdapter(adapter);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } else {
//                    Toast.makeText(DataPembayaranDebitur.this, "Response Kosong",Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(DataPembayaranDebitur.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        Volley.newRequestQueue(this).add(jsonArrayRequest);
//    }

}
