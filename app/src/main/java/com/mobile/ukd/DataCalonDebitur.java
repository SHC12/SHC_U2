package com.mobile.ukd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.mobile.ukd.adapter.TableAdapterCalonDebitur;
import com.mobile.ukd.admin.DashboardAdmin;
import com.mobile.ukd.admin.DetailCalonDebitur;
import com.mobile.ukd.admin.InsertPembayaran;
import com.mobile.ukd.api.ApiClient;
import com.mobile.ukd.api.ApiInterface;
import com.mobile.ukd.kabag.DashboardKabag;
import com.mobile.ukd.kabag.DetailCalonDebiturKabag;
import com.mobile.ukd.kabag.DetailDebiturAktif;
import com.mobile.ukd.model.Debitur;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataCalonDebitur extends AppCompatActivity {
    ApiInterface apiInterface;
    private RecyclerView rvCalonDebitur;
    private String flag;
    private TableAdapterCalonDebitur adapter;
    private TableAdapterCalonDebitur.RecyclerViewClickListener listener;
    private List<Debitur> debiturList;
    private TextView title, subTitle;
    private ProgressDialog progressDialog;
    private String URL_LENGKAP;

    String url_export_calon_debitur = "http://kristoforus.my.id/api_android/export_calon_debitur.php?status=1";
    String url_export_calon_debitur_kabag = "http://kristoforus.my.id/api_android/export_calon_debitur_kabag.php?status=1";
    String url_debitur_aktif = "http://kristoforus.my.id/api_android/export_debitur_disetujui.php";
    String url_debitur_ditolak = "http://kristoforus.my.id/api_android/export_debitur_tolak.php";





    Button btnExportPdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_calon_debitur);
        title = findViewById(R.id.title_data_debitur);
        subTitle = findViewById(R.id.sub_title_data_calon_debitur);
//        URL_KTP = URL_FILE_KTP + file_ktp;


        debiturList = new ArrayList<>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(this);
        rvCalonDebitur = findViewById(R.id.rv_data_calon_debitur);
        flag = getIntent().getStringExtra("flag");
        getFlag();
        btnExportPdf = findViewById(R.id.btnExportCalonDebitur);
        btnExportPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag.equals("admin")){
                    export_calon_debitur(url_export_calon_debitur, "calon_debitur_admin.pdf");
                }else if(flag.equals("kabag")){
                    export_calon_debitur(url_export_calon_debitur_kabag, "calon_debitur_kabag.pdf");
                }else if(flag.equals("debiturAktif")){
                    export_calon_debitur(url_debitur_aktif, "debitur_aktif.pdf");
                }else if(flag.equals("debiturDitolak")){
                    export_calon_debitur(url_debitur_ditolak, "debitur_ditolak.pdf");
                }

            }
        });


        listener = new TableAdapterCalonDebitur.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, int position) {
              if(flag.equals("admin")){
                    Intent intent = new Intent(DataCalonDebitur.this, DetailCalonDebitur.class);
                intent.putExtra(DetailCalonDebitur.DETAIL_DEBITUR,debiturList .get(position-1));
                startActivity(intent);
                }
                if(flag.equals("kabag")){
                    Intent intent = new Intent(DataCalonDebitur.this, DetailCalonDebiturKabag.class);
                intent.putExtra(DetailCalonDebiturKabag.DETAIL_DEBITUR_KABAG,debiturList .get(position-1));
                startActivity(intent);
              }
                if(flag.equals("debiturAktif")){
                    Intent intent = new Intent(DataCalonDebitur.this, DetailDebiturAktif.class);
                intent.putExtra(DetailDebiturAktif.DETAIL_DEBITUR_AKITF,debiturList .get(position-1));
                intent.putExtra("flag","aktif");
                startActivity(intent);
                }
                if(flag.equals("debiturDitolak")){
                    Intent intent = new Intent(DataCalonDebitur.this, DetailDebiturAktif.class);
                intent.putExtra(DetailDebiturAktif.DETAIL_DEBITUR_DITOLAK,debiturList .get(position-1));
                intent.putExtra("flag","ditolak");
                    startActivity(intent);
                }
                if(flag.equals("inputPembayaran")){
                    Intent intent = new Intent(DataCalonDebitur.this, InsertPembayaran.class);
                    intent.putExtra(InsertPembayaran.INSERT_PEMBAYARAN,debiturList .get(position-1));
                    startActivity(intent);
                }



            }
        };


        rvCalonDebitur.setHasFixedSize(true);
        rvCalonDebitur.setLayoutManager(new LinearLayoutManager(this));


    }

    private void getFlag(){
        if(flag.equals("admin")){
            title.setText("Data Calon Debitur");
            getCalonDebiturAdmin();
            subTitle.setText(R.string.datacalondebiturclick);
        } else if(flag.equals("kabag")) {
            title.setText("Data Calon Debitur");
            getCalonDebiturKabag();
            subTitle.setText("Klik pada nama untuk melihat detail calon debitur");
        } else if(flag.equals("debiturAktif")) {
            title.setText("Data Debitur Aktif");
            getCalonDebiturAktif();
            subTitle.setText("Klik pada nama untuk melihat detail debitur aktif data disusun berdasarkan tanggal pengajuan terbaru");
        } else if(flag.equals("debiturDitolak")){
            title.setText("Data Debitur Ditolak");
            getCalonDebiturDitolak();
            subTitle.setText("Klik pada nama untuk melihat detail debitur ditolak data disusun berdasarkan tanggal pengajuan terbaru");
        } else if(flag.equals("inputPembayaran")){
            title.setText("Input Pembayaran Debitur");
            getCalonDebiturAktif();
            subTitle.setText("Klik pada nama untuk memasukan data pembayaran");
        }
    }

    public void getCalonDebiturKabag(){
        Call<List<Debitur>> callCalonKabag = apiInterface.getCalonDeiturKabag();
        callCalonKabag.enqueue(new Callback<List<Debitur>>() {
            @Override
            public void onResponse(Call<List<Debitur>> call, Response<List<Debitur>> response) {
                Log.e("api",response.toString());
                debiturList = response.body();
                if(debiturList.isEmpty()){
                    Toast.makeText(DataCalonDebitur.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                }
                Log.i(DataCalonDebitur.class.getSimpleName(), response.body().toString());
            adapter = new TableAdapterCalonDebitur(debiturList,getApplicationContext(),listener);
                adapter.notifyDataSetChanged();
                rvCalonDebitur.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Debitur>> call, Throwable t) {
                Toast.makeText(DataCalonDebitur.this, "Terjadi kesalahan saat memuat data, Coba periksa Koneksi Internet Anda",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCalonDebiturAdmin(){
        Call<List<Debitur>> callCalonAdmin = apiInterface.getCalonDeiturAdmin();
        callCalonAdmin.enqueue(new Callback<List<Debitur>>() {
            @Override
            public void onResponse(Call<List<Debitur>> call, Response<List<Debitur>> response) {
            debiturList = response.body();
                if(debiturList.isEmpty()){
                    Toast.makeText(DataCalonDebitur.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                }
            adapter = new TableAdapterCalonDebitur(debiturList,getApplicationContext(),listener);
            rvCalonDebitur.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Debitur>> call, Throwable t) {
                Toast.makeText(DataCalonDebitur.this, "Terjadi kesalahan saat memuat data, Coba periksa Koneksi Internet Anda",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCalonDebiturAktif(){
        Call<List<Debitur>> callDebiturAktif = apiInterface.getDeiturAktif();
        callDebiturAktif.enqueue(new Callback<List<Debitur>>() {
            @Override
            public void onResponse(Call<List<Debitur>> call, Response<List<Debitur>> response) {
            debiturList = response.body();
                if(debiturList.isEmpty()){
                    Toast.makeText(DataCalonDebitur.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                }
            adapter = new TableAdapterCalonDebitur(debiturList,getApplicationContext(),listener);
            rvCalonDebitur.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Debitur>> call, Throwable t) {
                Toast.makeText(DataCalonDebitur.this, "Terjadi kesalahan saat memuat data, Coba periksa Koneksi Internet Anda",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCalonDebiturDitolak(){
        Call<List<Debitur>> callDebiturDitolak = apiInterface.getDeiturDitolak();
        callDebiturDitolak.enqueue(new Callback<List<Debitur>>() {
            @Override
            public void onResponse(Call<List<Debitur>> call, Response<List<Debitur>> response) {
            debiturList = response.body();
            if(debiturList.isEmpty()){
                Toast.makeText(DataCalonDebitur.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
            }
            adapter = new TableAdapterCalonDebitur(debiturList,getApplicationContext(),listener);
            rvCalonDebitur.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Debitur>> call, Throwable t) {
                Toast.makeText(DataCalonDebitur.this, "Terjadi kesalahan saat memuat data, Coba periksa Koneksi Internet Anda",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


/*
  private void getCalonDebitur() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_GET_CALON_DEBITUR, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("api",response.toString());
                progressDialog.dismiss();
                    try {
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject jsonObject = response.getJSONObject(i);
                            Debitur debitur = new Debitur();
                            debitur.setNo(jsonObject.getString("no"));
                            debitur.setKodeDebitur(jsonObject.getString("id_format_debitur"));
                            debitur.setIdDebitur(jsonObject.getString("id_debitur"));
                            debitur.setId_user(jsonObject.getString("id_user"));
                            debitur.setNamaDebitur(jsonObject.getString("nama_lengkap"));
                            debitur.setNik(jsonObject.getString("nik"));
                            debitur.setFileKtp(jsonObject.getString("scan_ktp"));
                            debitur.setNoNpwp(jsonObject.getString("npwp"));
                            debitur.setFileNpwp(jsonObject.getString("scan_npwp"));
                            debitur.setNoHp(jsonObject.getString("no_hp"));
                            debitur.setAlamat(jsonObject.getString("alamat"));
                            debitur.setEmail(jsonObject.getString("email"));
                            debitur.setNominal(jsonObject.getString("nominal_debitur"));
                            debitur.setUsername(jsonObject.getString("username"));
                            debitur.setPassword(jsonObject.getString("password"));
                            debitur.setTenorBulan(jsonObject.getString("tenor"));
                            debitur.setTanggalPengajuan(jsonObject.getString("tanggal_pengajuan"));
                            debitur.setStatus(jsonObject.getString("status"));

                            debiturList.add(debitur);
                        }
                        adapter = new TableAdapterCalonDebitur(debiturList,getApplicationContext(),listener);
                        rvCalonDebitur.setAdapter(adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DataCalonDebitur.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);

    }

 */

   /* private void getCalonDebitur() {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL_GET_CALON_DEBITUR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("api",response.toString());
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Debitur debitur = new Debitur(
                        jsonObject.getString("no"),
                        jsonObject.getString("id_format_debitur"),
                        jsonObject.getString("id_debitur"),
                        jsonObject.getString("id_user"),
                        jsonObject.getString("nama_lengkap"),
                        jsonObject.getString("nik"),
                        jsonObject.getString("scan_ktp"),
                        jsonObject.getString("npwp"),
                        jsonObject.getString("scan_npwp"),
                       jsonObject.getString("no_hp"),
                        jsonObject.getString("alamat"),
                        jsonObject.getString("email"),
                        jsonObject.getString("nominal_debitur"),
                        jsonObject.getString("username"),
                        jsonObject.getString("password"),
                        jsonObject.getString("tenor"),
                        jsonObject.getString("tanggal_pengajuan"),
                        jsonObject.getString("status")
                        );

                        debiturList.add(debitur);
                    }
                    adapter = new TableAdapterCalonDebitur(debiturList,getApplicationContext(),listener);
                    rvCalonDebitur.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(DataCalonDebitur.this, "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    */

    public void export_calon_debitur(String aUrl, String filename) {
        AndroidNetworking.download(aUrl, "/storage/emulated/0/Download/", filename)
                .setTag("downloadTest")
                .setPriority(Priority.MEDIUM)
                .addHeaders("Authorization", "Basic YnNyZTpzZWN1cmV0cmFuc2FjdGlvbnMhISE=")
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        Toast.makeText(DataCalonDebitur.this, "Mengunduh File "+filename, Toast.LENGTH_SHORT).show();
                        // do anything with progress
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
//                        progressDialog.dismiss();
                        Toast.makeText(DataCalonDebitur.this, "File "+filename+" selesai di download", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFlag();
    }

   @Override
   public void onBackPressed() {
       super.onBackPressed();
       if(flag.equals("admin")){
           startActivity(new Intent(DataCalonDebitur.this, DashboardAdmin.class));
       }else if(flag.equals("kabag")){
           startActivity(new Intent(DataCalonDebitur.this, DashboardKabag.class));
       }else if(flag.equals("debiturAktif")){
           startActivity(new Intent(DataCalonDebitur.this, DashboardKabag.class));
       }else if(flag.equals("debiturDitolak")){
           startActivity(new Intent(DataCalonDebitur.this, DashboardKabag.class));
       }

  }


}
