package com.mobile.ukd.kabag;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.google.android.material.button.MaterialButton;
import com.mobile.ukd.DataCalonDebitur;
import com.mobile.ukd.GaleryViewer;
import com.mobile.ukd.PDFViewer;
import com.mobile.ukd.R;
import com.mobile.ukd.admin.DetailCalonDebitur;
import com.mobile.ukd.model.Debitur;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailCalonDebiturKabag extends AppCompatActivity {

    public static final String DETAIL_DEBITUR_KABAG = "detail_debitur_kabag";
    private String s_idUser,s_status,s_kode,s_tgl,s_nama,s_nik,s_scanKtp,s_npwp,s_scanNpwp,s_noHp,s_alamat,s_email,s_nominal,s_tenor,s_username,s_password,result_verif;
    private TextView kodeDebitur,tglPengajuan,nama,nik,scanKtp,npwp,scanNpwp,noHp,alamat,email,nominal,tenor,username,password;
    private Spinner status;
    private String URL_UPDATE ="http://kristoforus.my.id/api_android/update_calon_debitur_kabag.php";
    private MaterialButton btnUpdateKabag;
    private ProgressDialog progressDialog;
    private String URL_FILE_KTP = "http://kristoforus.my.id/scan_ktp/";
    private String URL_FILE_NPWP = "http://kristoforus.my.id/scan_npwp/";
    private String URL_LENGKAP_KTP;
    private String URL_LENGKAP_NPWP;
    private String extension;
    private String extensionNPWP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_calon_debitur_kabag);


        kodeDebitur = findViewById(R.id.tx_kode_debitur_detail_kabag);
        tglPengajuan = findViewById(R.id.tanggalPengajuanDetailKabag);
        nama = findViewById(R.id.namaDetailCalonDebiturKabag);
        nik = findViewById(R.id.nikDetailCalonDebiturKabag);
        scanKtp = findViewById(R.id.fileKtpDetailCalonDebiturKabag);
        npwp = findViewById(R.id.noNpwpDetailCalonDebiturKabag);
        scanNpwp = findViewById(R.id.fileNpwpDetailCalonDebiturKabag);
        noHp = findViewById(R.id.noHpDetailCalonDebiturKabag);
        alamat = findViewById(R.id.alamarDetailCalonDebiturKabag);
        email = findViewById(R.id.emailDetailCalonDebiturKabag);
        nominal = findViewById(R.id.nominalDetailCalonDebiturKabag);
        tenor = findViewById(R.id.tenorBulanDetailCalonDebiturKabag);
        username = findViewById(R.id.usernameDetailCalonDebiturKabag);
        password = findViewById(R.id.passwordDetailCalonDebiturKabag);
        status = findViewById(R.id.statusDetailCalonDebiturSpinner);
        Debitur debitur = getIntent().getParcelableExtra(DETAIL_DEBITUR_KABAG);
        s_idUser = debitur.getId_user();
        s_status = debitur.getStatus();
        s_kode = debitur.getKodeDebitur();
        s_tgl = debitur.getTanggalPengajuan();
        s_nama = debitur.getNamaDebitur();
        s_nik = debitur.getNik();
        s_scanKtp = debitur.getFileKtp();
        s_npwp = debitur.getNoNpwp();
        s_scanNpwp = debitur.getFileNpwp();
        s_noHp = debitur.getNoHp();
        s_alamat = debitur.getAlamat();
        s_email = debitur.getEmail();
        s_nominal = debitur.getNominal();
        s_tenor = debitur.getTenorBulan();
        s_username = debitur.getUsername();
        s_password = debitur.getPassword();

        String[] items = {"Proses", "Disetujui", "Ditolak"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, items);
        status.setAdapter(arrayAdapter);
        if (s_status.equals("1")) {
            status.setSelection(arrayAdapter.getPosition("Proses"));
        }
        else if (s_status.equals("2")) {
            status.setSelection(arrayAdapter.getPosition("Disetujui"));
        }
        else if (s_status.equals("3")) {
            status.setSelection(arrayAdapter.getPosition("Ditolak"));
        }

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (status.getSelectedItem().toString().equals("Ditolak")) {
                    result_verif = "3";
                } else if (status.getSelectedItem().toString().equals("Disetujui")) {
                    result_verif = "2";
                } else {
                    result_verif = "1";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        kodeDebitur.setText("Kode Debitur : "+s_kode);
        tglPengajuan.setText(s_tgl);
        nama.setText(s_nama);
        nik.setText(s_nik);
        scanKtp.setText(s_scanKtp);
        npwp.setText(s_npwp);
        scanNpwp.setText(s_scanNpwp);
        noHp.setText(s_noHp);
        alamat.setText(s_alamat);
        email.setText(s_email);
        nominal.setText(s_nominal);
        tenor.setText(s_tenor);
        username.setText(s_username);
        password.setText(s_password);

        progressDialog = new ProgressDialog(DetailCalonDebiturKabag.this);
        btnUpdateKabag = findViewById(R.id.btnCalonDebiturKabag);
        btnUpdateKabag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Mohon Tunggu....");
                progressDialog.show();
                updateStatus(s_idUser,result_verif);
            }
        });

        URL_LENGKAP_KTP = URL_FILE_KTP+s_scanKtp;
        URL_LENGKAP_NPWP = URL_FILE_NPWP+s_scanNpwp;

        extension = s_scanKtp.substring(s_scanKtp.lastIndexOf("."));
        extensionNPWP = s_scanNpwp.substring(s_scanNpwp.lastIndexOf("."));



        scanKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extension.equals(".pdf")) {
                    download_ktp(URL_LENGKAP_KTP, s_scanKtp);
                }
                if(extension.equals(".jpg")){
                    Intent intent = new Intent(DetailCalonDebiturKabag.this, GaleryViewer.class);
                    intent.putExtra("urlImage",URL_LENGKAP_KTP);
                    intent.putExtra("nameFile",s_scanKtp);
                    startActivity(intent);
                }
                if(extension.equals(".png")){
                    Intent intent = new Intent(DetailCalonDebiturKabag.this, GaleryViewer.class);
                    intent.putExtra("urlImage",URL_LENGKAP_KTP);
                    intent.putExtra("nameFile",s_scanKtp);
                    startActivity(intent);
                }
            }
        });
        scanNpwp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extensionNPWP.equals(".pdf")) {
                    download_npwp(URL_LENGKAP_NPWP, s_scanNpwp);
                }
                if(extensionNPWP.equals(".jpg")){
                    Intent intent = new Intent(DetailCalonDebiturKabag.this, GaleryViewer.class);
                    intent.putExtra("urlImage",URL_LENGKAP_NPWP);
                    intent.putExtra("nameFile",s_scanNpwp);
                    startActivity(intent);
                }
                if(extensionNPWP.equals(".png")){
                    Intent intent = new Intent(DetailCalonDebiturKabag.this, GaleryViewer.class);
                    intent.putExtra("urlImage",URL_LENGKAP_NPWP);
                    intent.putExtra("nameFile",s_scanNpwp);
                    startActivity(intent);
                }
            }
        });
    }

    private void updateStatus(final String id_user,final String result_verif) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("status");
                    if (success.equals("1")) {
                        Toast.makeText(DetailCalonDebiturKabag.this, "Update Status Berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailCalonDebiturKabag.this,DataCalonDebitur.class);
                        intent.putExtra("flag","kabag");
                        startActivity(intent);
                    } else {
                        Toast.makeText(DetailCalonDebiturKabag.this, "Update Status Gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params = new HashMap<>();
                params.put("id_user",id_user);
                params.put("status",result_verif);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void download_ktp(String aUrl, String filename) {
        AndroidNetworking.download(aUrl, "/storage/emulated/0/Download/", filename)
                .setTag("downloadTest")
                .setPriority(Priority.MEDIUM)
                .addHeaders("Authorization", "Basic YnNyZTpzZWN1cmV0cmFuc2FjdGlvbnMhISE=")
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        // do anything with progress
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
//                        progressDialog.dismiss();
                        Toast.makeText(DetailCalonDebiturKabag.this, filename+" selesai di download", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailCalonDebiturKabag.this, PDFViewer.class);
                        intent.putExtra("urlKtp",URL_LENGKAP_KTP);
                        intent.putExtra("ktp",s_scanKtp);
                        intent.putExtra("trigger","ktp");
                        startActivity(intent);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
    public void download_npwp(String aUrl, String filename) {
        AndroidNetworking.download(aUrl, "/storage/emulated/0/Download/", filename)
                .setTag("downloadTest")
                .setPriority(Priority.MEDIUM)
                .addHeaders("Authorization", "Basic YnNyZTpzZWN1cmV0cmFuc2FjdGlvbnMhISE=")
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {

                        // do anything with progress
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
//                        progressDialog.dismiss();
                        Toast.makeText(DetailCalonDebiturKabag.this, filename+" selesai di download", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailCalonDebiturKabag.this, PDFViewer.class);
                        intent.putExtra("urlNpwp",URL_LENGKAP_NPWP);
                        intent.putExtra("npwp",s_scanNpwp);
                        intent.putExtra("trigger","npwp");
                        startActivity(intent);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
}
